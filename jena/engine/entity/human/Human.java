package jena.engine.entity.human;

import jena.engine.graphics.GraphicsClipPainter;
import jena.engine.graphics.GraphicsResource;
import jena.engine.entity.FrameStartListener;

import java.util.Arrays;

import jena.editor.GraphicsInspectable;
import jena.editor.GraphicsInspector;
import jena.engine.entity.Controller;
import jena.engine.entity.DeltaTime;
import jena.engine.entity.FrameEndListener;
import jena.engine.entity.Time;
import jena.engine.graphics.ColorFloatStruct;
import jena.engine.graphics.GraphicsClip;
import jena.engine.graphics.TextureHandle;
import jena.engine.graphics.Transformation;
import jena.engine.io.Storage;
import jena.engine.math.FieldVector2f;
import jena.engine.math.IntAcceptor;
import jena.engine.math.Matrix3f;
import jena.engine.math.Matrix3fTransform;
import jena.engine.math.Matrix3fTranslation;
import jena.engine.math.Rectf;
import jena.engine.math.ValueFloat;
import jena.engine.math.ValueInt;
import jena.engine.math.Vector2f;
import jena.engine.math.Vector2fAdd;
import jena.engine.math.Vector2fLength;
import jena.engine.math.Vector2fStruct;
import jena.engine.graphics.MultiplicationTransformation;

public class Human implements GraphicsClipPainter, FrameStartListener, FrameEndListener, GraphicsInspectable
{
    private interface BodyPart extends GraphicsClipPainter, GraphicsInspectable
    {
    }
    private class DefaultBodyPart implements BodyPart
    {
        Rectf source;
        Rectf destination;
        Transformation transformation;
        BodyPart[] children;

        public DefaultBodyPart(Rectf source, Rectf destination, Matrix3f transform, BodyPart...children)
        {
            this.source = source;
            this.destination = destination;
            this.transformation = new MultiplicationTransformation(transform);
            this.children = children;
        }

        @Override
        public void paint(GraphicsClip clip)
        {
            clip.matrixScope(transformation, () ->
            {
                clip.drawSprite(texture, source, destination);
                for(GraphicsClipPainter child : children)
                {
                    child.paint(clip);
                }
            });
        }

        @Override
        public GraphicsClipPainter inspect(GraphicsInspector inspector)
        {
            return inspector.matrixScope(transformation, () ->
            {
                GraphicsClipPainter point = inspector.pointHandle(new Vector2fStruct(), new ColorFloatStruct(1f, 1f, 0f, 1f), a -> a.call(0.1f));
                GraphicsClipPainter[] nested = Arrays.stream(children).map(c -> c.inspect(inspector)).toArray(GraphicsClipPainter[]::new);
                return clip ->
                {
                    point.paint(clip);
                    for(GraphicsClipPainter n : nested) n.paint(clip);
                };
            });
        }
    }

    private TextureHandle texture;
    private BodyPart root;
    private Vector2fStruct position;
    private Vector2f movement;
    private ValueFloat deltaTime;
    private Vector2f walkFieldPoint;

    public Human(GraphicsResource graphicsResource, Storage storage, Controller controller, FieldVector2f walkField)
    {
        texture = graphicsResource.loadTexture(storage.open("HumanMap.png"));
        position = new Vector2fStruct();
        movement = controller.movement();
        walkFieldPoint = walkField.project(position);

        ValueFloat time = new Time();
        deltaTime = new DeltaTime(time);

        ValueFloat movementLength = new Vector2fLength(movement);
        ValueFloat sin = time.mul(6f).sin().mul(0.5f).mul(movementLength);
        ValueInt dir = new ValueInt()
        {
            int dir = 1;
            public void accept(IntAcceptor acceptor)
            {
                movement.accept((x, y) -> dir = x == 0 ? dir : (x < 0 ? -1 : 1));
                acceptor.call(dir);
            }
        };

        BodyPart head = new DefaultBodyPart(a -> a.call(0.8f, 0f, 0.2f, 0.5f), a -> a.call(-0.5f, -0.2f, 1f, 1f), new Matrix3fTransform(a -> a.call(-0.05f, 0.35f), a -> a.call(0.5f, 0.75f), a -> movement.accept((x, y) -> a.call(y * 0.5f))));

        BodyPart forearm = new DefaultBodyPart(a -> a.call(0.6f, 0.5f, 0.2f, 0.5f), a -> a.call(-0.5f, -0.8f, 1f, 1f), new Matrix3fTransform(a -> a.call(0f, -1f), a -> a.call(0f)));
        BodyPart armL = new DefaultBodyPart(a -> a.call(0.6f, 0f, 0.2f, 0.5f), a -> a.call(-0.5f, -1f, 1f, 1f), new Matrix3fTransform(a -> a.call(-0.3f, 0.5f), a -> a.call(0.5f, 0.5f), sin), forearm);
        BodyPart armR = new DefaultBodyPart(a -> a.call(0.6f, 0f, 0.2f, 0.5f), a -> a.call(-0.5f, -1f, 1f, 1f), new Matrix3fTransform(a -> a.call(0.1f, 0.5f), a -> a.call(0.5f, 0.5f), a -> sin.accept(s -> a.call(-s))), forearm);

        BodyPart shoe = new DefaultBodyPart(a -> a.call(0.8f, 0.5f, 0.2f, 0.5f), a -> a.call(-0.5f, -0.8f, 1f, 1f), new Matrix3fTransform(a -> a.call(0f, -0.5f), a -> a.call(0f)));
        BodyPart knee = new DefaultBodyPart(a -> a.call(0.4f, 0.5f, 0.2f, 0.5f), a -> a.call(-0.5f, -0.8f, 1f, 1f), new Matrix3fTransform(a -> a.call(0f, -0.8f), a -> a.call(0f)), shoe);
        BodyPart legL = new DefaultBodyPart(a -> a.call(0.4f, 0f, 0.2f, 0.5f), a -> a.call(-0.5f, -0.8f, 1f, 1.3f), new Matrix3fTransform(a -> a.call(-0.15f, -0.45f), a -> a.call(0.5f, 0.5f), a -> sin.accept(s -> a.call(-s))), knee);
        BodyPart legR = new DefaultBodyPart(a -> a.call(0.4f, 0f, 0.2f, 0.5f), a -> a.call(-0.5f, -0.8f, 1f, 1.3f), new Matrix3fTransform(a -> a.call(0.1f, -0.45f), a -> a.call(0.5f, 0.5f), sin), knee);
        BodyPart body = new DefaultBodyPart(a -> a.call(0f, 0f, 0.4f, 1f), a -> a.call(-0.5f, -0.5f, 0.8f, 1.2f), new Matrix3fTransform(a -> a.call(0f, 0f), a -> a.call(1f, 1f), a -> a.call(0f)), head);
        
        Transformation transformation = new MultiplicationTransformation(new Matrix3fTranslation(new Vector2fAdd(position, a -> a.call(0f, 1.25f))).scale(a -> dir.accept(d -> a.call(d, 1f))));

        root = new BodyPart()
        {
            @Override
            public void paint(GraphicsClip clip)
            {
                //clip.fillEllipse(a -> a.call(position.x - 0.25f, position.y - 0.25f, 0.5f, 0.5f), a -> a.call(50, 150, 50, 255));
                //clip.drawEllipse(a -> a.call(position.x - 0.5f, position.y - 0.5f, 1f, 1f), a -> a.call(150, 0, 150, 255), a -> a.call(0.02f));
                //clip.drawLine(position, a -> a.call(0f, 0f), a -> a.call(150, 150, 0, 255), a -> a.call(0.01f));
                clip.matrixScope(transformation, () ->
                {
                    armR.paint(clip);
                    legR.paint(clip);
                    body.paint(clip);
                    legL.paint(clip);
                    armL.paint(clip);
                });    
            }

            @Override
            public GraphicsClipPainter inspect(GraphicsInspector inspector)
            {
                return inspector.matrixScope(transformation, () ->
                {
                    GraphicsInspectable[] inspectables = {armR, legR, body, legL, armL};
                    GraphicsClipPainter[] painters = Arrays.stream(inspectables)
                        .map(i -> i.inspect(inspector))
                        .toArray(GraphicsClipPainter[]::new);
                    return clip ->
                    {
                        for(GraphicsClipPainter painter : painters)
                        {
                            painter.paint(clip);
                        }
                    };
                });
            }
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
        movement.accept((x, y) -> deltaTime.accept(dt ->
        {
            position.x += x * dt * 3f;
            position.y += y * dt * 3f;
        }));
        walkFieldPoint.accept((x, y) ->
        {
            position.x = x;
            position.y = y;
        });
    }

    public Vector2f position()
    {
        return position;
    }

    @Override
    public GraphicsClipPainter inspect(GraphicsInspector inspector)
    {
        return root.inspect(inspector);
    }
}