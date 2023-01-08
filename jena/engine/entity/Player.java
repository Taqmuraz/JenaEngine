package jena.engine.entity;

import jena.engine.graphics.GraphicsClipPainter;
import jena.engine.graphics.GraphicsResource;
import jena.engine.graphics.ColorByteStruct;
import jena.engine.graphics.GraphicsClip;
import jena.engine.graphics.TextureHandle;
import jena.engine.input.Key;
import jena.engine.input.Keyboard;
import jena.engine.io.StorageFileResource;
import jena.engine.math.Matrix3f;
import jena.engine.math.Matrix3fBuilder;
import jena.engine.math.Matrix3fMul;
import jena.engine.math.Matrix3fTransform;
import jena.engine.math.Rectf;
import jena.engine.math.Vector2f;
import jena.engine.math.Vector2fAcceptor;
import jena.engine.math.Vector2fStruct;

public class Player implements GraphicsClipPainter
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
    private TimeMeter frameTimeMeter;

    public Player(GraphicsResource graphicsResource, Keyboard keyboard)
    {
        texture = graphicsResource.loadTexture(new StorageFileResource("HumanMap.png"));
        position = new Vector2fStruct();
        Vector2f movement = new Vector2f()
        {
            Key w = keyboard.keyOf('W');
            Key a = keyboard.keyOf('A');
            Key s = keyboard.keyOf('S');
            Key d = keyboard.keyOf('D');

            @Override
            public void accept(Vector2fAcceptor acceptor)
            {
                float x = 0f;
                float y = 0f;
                if (w.isHold()) y += 1f;
                if (s.isHold()) y -= 1f;
                if (a.isHold()) x -= 1f;
                if (d.isHold()) x += 1f;
                acceptor.call(x, y);
            }
        };

        BodyPart head = new BodyPart(a -> a.call(0.8f, 0f, 0.2f, 0.5f), a -> a.call(-0.5f, -0.2f, 1f, 1f), a -> new Matrix3fTransform(-0.05f, 0.35f, 0.5f, 0.75f, (float)Math.sin(Time.time() * 2f) * 0.25f).accept(a));

        BodyPart forearm = new BodyPart(a -> a.call(0.6f, 0.5f, 0.2f, 0.5f), a -> a.call(-0.5f, -0.8f, 1f, 1f), new Matrix3fTransform(0f, -1f, 0f));
        BodyPart armL = new BodyPart(a -> a.call(0.6f, 0f, 0.2f, 0.5f), a -> a.call(-0.5f, -1f, 1f, 1f), a -> new Matrix3fTransform(-0.3f, 0.5f, 0.5f, 0.5f, (float)Math.sin(Time.time() * 2f) * 0.5f).accept(a), forearm);
        BodyPart armR = new BodyPart(a -> a.call(0.6f, 0f, 0.2f, 0.5f), a -> a.call(-0.5f, -1f, 1f, 1f), a -> new Matrix3fTransform(0.1f, 0.5f, 0.5f, 0.5f, -(float)Math.sin(Time.time() * 2f) * 0.5f).accept(a), forearm);

        BodyPart shoe = new BodyPart(a -> a.call(0.8f, 0.5f, 0.2f, 0.5f), a -> a.call(-0.5f, -0.8f, 1f, 1f), new Matrix3fTransform(0f, -0.5f, 0f));
        BodyPart knee = new BodyPart(a -> a.call(0.4f, 0.5f, 0.2f, 0.5f), a -> a.call(-0.5f, -0.8f, 1f, 1f), new Matrix3fTransform(0f, -0.8f, 0f), shoe);
        BodyPart legL = new BodyPart(a -> a.call(0.4f, 0f, 0.2f, 0.5f), a -> a.call(-0.5f, -0.8f, 1f, 1.3f), a -> new Matrix3fTransform(-0.15f, -0.45f, 0.5f, 0.5f, -(float)Math.sin(Time.time() * 2f) * 0.5f).accept(a), knee);
        BodyPart legR = new BodyPart(a -> a.call(0.4f, 0f, 0.2f, 0.5f), a -> a.call(-0.5f, -0.8f, 1f, 1.3f), a -> new Matrix3fTransform(0.1f, -0.45f, 0.5f, 0.5f, (float)Math.sin(Time.time() * 2f) * 0.5f).accept(a), knee);
        BodyPart body = new BodyPart(a -> a.call(0f, 0f, 0.4f, 1f), a -> a.call(-0.5f, -0.5f, 0.8f, 1.2f), new Matrix3fTransform(0f, 0f, 1f, 1f, 0f), head);
        root = clip ->
        {
            clip.fillRect(a -> a.call(-0.5f, -0.5f, 1f, 1f), new ColorByteStruct(0, 0, 0, 255));
            movement.accept((x, y)->
            {
                float d = frameTimeMeter.measureTime();
                position.x += x * d;
                position.y += y * d;
            });
            clip.matrixScope(source -> new Matrix3fBuilder(source).translate(position).scale(a -> a.call(0.25f, 0.25f)).build(), () ->
            {
                armR.paint(clip);
                legR.paint(clip);
                body.paint(clip);
                legL.paint(clip);
                armL.paint(clip);
            });
        };
        frameTimeMeter = new DefaultTimeMeter();
    }

    @Override
    public void paint(GraphicsClip clip)
    {
        root.paint(clip);
    }
}