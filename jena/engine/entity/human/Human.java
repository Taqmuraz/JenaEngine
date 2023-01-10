package jena.engine.entity.human;

import jena.engine.graphics.GraphicsClipPainter;
import jena.engine.graphics.GraphicsResource;
import jena.engine.entity.FrameStartHandler;
import jena.engine.entity.Controller;
import jena.engine.entity.DefaultTimeMeter;
import jena.engine.entity.FrameEndHandler;
import jena.engine.entity.Time;
import jena.engine.entity.TimeMeter;
import jena.engine.graphics.GraphicsClip;
import jena.engine.graphics.TextureHandle;
import jena.engine.io.StorageFileResource;
import jena.engine.math.Matrix3f;
import jena.engine.math.Matrix3fBuilder;
import jena.engine.math.Matrix3fMul;
import jena.engine.math.Matrix3fTransform;
import jena.engine.math.Rectf;
import jena.engine.math.ValueFloat;
import jena.engine.math.ValueInt;
import jena.engine.math.Vector2f;
import jena.engine.math.Vector2fStruct;

public class Human implements GraphicsClipPainter, FrameStartHandler, FrameEndHandler
{
    private class BodyPart implements GraphicsClipPainter
    {
        Rectf source;
        Rectf destination;
        Matrix3f transform;
        GraphicsClipPainter[] children;

        public BodyPart(Rectf source, Rectf destination, Matrix3f transform, GraphicsClipPainter...children)
        {
            this.source = source;
            this.destination = destination;
            this.transform = transform;
            this.children = children;
        }

        @Override
        public void paint(GraphicsClip clip)
        {
            clip.matrixScope(source -> new Matrix3fMul(source, transform), () ->
            {
                clip.drawSprite(texture, source, destination);
                for(GraphicsClipPainter child : children)
                {
                    child.paint(clip);
                }
            });
        }
    }

    private TextureHandle texture;
    private GraphicsClipPainter root;
    private Vector2fStruct position;
    private Vector2f movement;
    private TimeMeter frameMeter;

    public Human(GraphicsResource graphicsResource, Controller controller)
    {
        texture = graphicsResource.loadTexture(new StorageFileResource("HumanMap.png"));
        position = new Vector2fStruct();
        movement = controller.movement();
        
        frameMeter = new DefaultTimeMeter();

        ValueFloat sin = () -> (float)Math.sin(Time.time() * 6f) * 0.5f * new Vector2fStruct(movement).length();
        ValueInt dir = new ValueInt()
        {
            int dir = 1;
            public int read()
            {
                movement.accept((x, y) -> dir = x == 0 ? dir : (x < 0 ? -1 : 1));
                return dir;
            }
        };

        BodyPart head = new BodyPart(a -> a.call(0.8f, 0f, 0.2f, 0.5f), a -> a.call(-0.5f, -0.2f, 1f, 1f), a -> new Matrix3fTransform(-0.05f, 0.35f, 0.5f, 0.75f, new Vector2fStruct(movement).y * 0.5f).accept(a));

        BodyPart forearm = new BodyPart(a -> a.call(0.6f, 0.5f, 0.2f, 0.5f), a -> a.call(-0.5f, -0.8f, 1f, 1f), new Matrix3fTransform(0f, -1f, 0f));
        BodyPart armL = new BodyPart(a -> a.call(0.6f, 0f, 0.2f, 0.5f), a -> a.call(-0.5f, -1f, 1f, 1f), a -> new Matrix3fTransform(-0.3f, 0.5f, 0.5f, 0.5f, sin.read()).accept(a), forearm);
        BodyPart armR = new BodyPart(a -> a.call(0.6f, 0f, 0.2f, 0.5f), a -> a.call(-0.5f, -1f, 1f, 1f), a -> new Matrix3fTransform(0.1f, 0.5f, 0.5f, 0.5f, -sin.read()).accept(a), forearm);

        BodyPart shoe = new BodyPart(a -> a.call(0.8f, 0.5f, 0.2f, 0.5f), a -> a.call(-0.5f, -0.8f, 1f, 1f), new Matrix3fTransform(0f, -0.5f, 0f));
        BodyPart knee = new BodyPart(a -> a.call(0.4f, 0.5f, 0.2f, 0.5f), a -> a.call(-0.5f, -0.8f, 1f, 1f), new Matrix3fTransform(0f, -0.8f, 0f), shoe);
        BodyPart legL = new BodyPart(a -> a.call(0.4f, 0f, 0.2f, 0.5f), a -> a.call(-0.5f, -0.8f, 1f, 1.3f), a -> new Matrix3fTransform(-0.15f, -0.45f, 0.5f, 0.5f, -sin.read()).accept(a), knee);
        BodyPart legR = new BodyPart(a -> a.call(0.4f, 0f, 0.2f, 0.5f), a -> a.call(-0.5f, -0.8f, 1f, 1.3f), a -> new Matrix3fTransform(0.1f, -0.45f, 0.5f, 0.5f, sin.read()).accept(a), knee);
        BodyPart body = new BodyPart(a -> a.call(0f, 0f, 0.4f, 1f), a -> a.call(-0.5f, -0.5f, 0.8f, 1.2f), new Matrix3fTransform(0f, 0f, 1f, 1f, 0f), head);
        
        root = clip ->
        {
            clip.fillEllipse(a -> a.call(position.x - 0.25f, position.y - 0.25f, 0.5f, 0.5f), a -> a.call(50, 150, 50, 255));
            clip.drawEllipse(a -> a.call(position.x - 0.5f, position.y - 0.5f, 1f, 1f), a -> a.call(150, 0, 150, 255), () -> 0.02f);
            clip.drawLine(position, a -> a.call(0f, 0f), a -> a.call(150, 150, 0, 255), () -> 0.01f);
            clip.matrixScope(source -> new Matrix3fBuilder(source).translate(position).scale(a -> a.call(0.25f * dir.read(), 0.25f)).build(), () ->
            {
                armR.paint(clip);
                legR.paint(clip);
                body.paint(clip);
                legL.paint(clip);
                armL.paint(clip);
            });
        };
    }

    @Override
    public void paint(GraphicsClip clip)
    {
        root.paint(clip);
    }

    @Override
    public void onEndFrame()
    {

    }

    @Override
    public void onStartFrame()
    {
        float dt = frameMeter.measureTime();
        movement.accept((x, y) ->
        {
            position.x += x * dt;
            position.y += y * dt;
        });
    }
}