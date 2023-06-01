package jena.game.human;

import jena.engine.graphics.GraphicsBrushPainter;
import jena.engine.graphics.GraphicsDrawingPainter;
import jena.engine.graphics.GraphicsPainter;
import jena.engine.graphics.GraphicsResource;
import jena.engine.graphics.MatrixScopeGraphicsPainter;
import jena.editor.GraphicsInspectable;
import jena.editor.GraphicsInspector;
import jena.engine.common.ArrayIterable;
import jena.engine.common.CachedIterable;
import jena.engine.common.MapIterable;
import jena.engine.graphics.ColorFloatStruct;
import jena.engine.graphics.CompositeGraphicsBrushPainter;
import jena.engine.graphics.CompositeGraphicsPainter;
import jena.engine.graphics.GraphicsBrush;
import jena.engine.graphics.TextureHandle;
import jena.engine.io.Storage;
import jena.engine.math.FloatAcceptor;
import jena.engine.math.Matrix3f;
import jena.engine.math.Matrix3fIdentity;
import jena.engine.math.Matrix3fMul;
import jena.engine.math.Matrix3fScale;
import jena.engine.math.Matrix3fTransform;
import jena.engine.math.Matrix3fTranslation;
import jena.engine.math.Rectf;
import jena.engine.math.RectfStruct;
import jena.engine.math.ValueFloat;
import jena.engine.math.ValueFloatAdd;
import jena.engine.math.ValueFloatMul;
import jena.engine.math.ValueFloatNegative;
import jena.engine.math.ValueFloatSin;
import jena.engine.math.ValueFloatStruct;
import jena.engine.math.ValueFloatSub;
import jena.engine.math.ValueFloatVector2fY;
import jena.engine.math.Vector2f;
import jena.engine.math.Vector2fAdd;
import jena.engine.math.Vector2fLength;
import jena.engine.math.Vector2fStruct;
import jena.engine.math.Vector2fValueFloat;
import jena.game.Time;
import jena.engine.graphics.NoneGraphicsPainter;

public class Human implements GraphicsBrushPainter, GraphicsInspectable
{
    private interface BodyPart extends GraphicsBrushPainter, GraphicsInspectable
    {
    }
    private class DefaultBodyPart implements BodyPart
    {
        Rectf source;
        Rectf destination;
        Matrix3f transform;
        BodyPart[] children;

        public DefaultBodyPart(Rectf source, Rectf destination, Matrix3f transform, BodyPart...children)
        {
            this.source = source;
            this.destination = destination;
            this.transform = transform;
            this.children = children;
        }

        @Override
        public GraphicsPainter paint(GraphicsBrush clip)
        {
            return new MatrixScopeGraphicsPainter(transform,
                new CompositeGraphicsPainter(
                    new GraphicsDrawingPainter(clip.drawSprite(texture, source, destination)),
                    new CompositeGraphicsBrushPainter(children).paint(clip)));
        }

        @Override
        public GraphicsPainter inspect(GraphicsInspector inspector)
        {
            return new MatrixScopeGraphicsPainter(transform,
                new CompositeGraphicsPainter(
                    inspector.pointHandle(new Vector2fStruct(), new ColorFloatStruct(1f, 1f, 0f, 1f), a -> a.call(0.1f)),
                    new CompositeGraphicsPainter(
                        new CachedIterable<>(
                            new MapIterable<>(
                                new ArrayIterable<>(children),
                                c -> c.inspect(inspector)))))
            );
        }
    }

    private TextureHandle texture;
    private BodyPart root;

    public Human(GraphicsResource graphicsResource, Storage storage, Vector2f position, Vector2f movement)
    {
        texture = graphicsResource.loadTexture(storage.open("HumanMap.png"));

        ValueFloat time = new Time();

        ValueFloat movementLength = new Vector2fLength(movement);
        ValueFloat sin = new ValueFloatMul(
            new ValueFloatSin(
                new ValueFloatMul(
                    time,
                    new ValueFloatStruct(6f))),
            new ValueFloatMul(
                movementLength,
                new ValueFloatStruct(0.5f)));

        ValueFloat dir = new ValueFloat()
        {
            float dir = 1f;
            public void accept(FloatAcceptor acceptor)
            {
                movement.accept((x, y) -> dir = x == 0 ? dir : (x < 0 ? -1 : 1));
                acceptor.call(dir);
            }
        };

        BodyPart head = new DefaultBodyPart(
            new RectfStruct(0.8f, 0f, 0.2f, 0.5f),
            new RectfStruct(-0.5f, -0.2f, 1f, 1f),
            new Matrix3fTransform(
                new Vector2fStruct(-0.05f, 0.35f),
                new Vector2fStruct(0.5f, 0.75f),
                new ValueFloatMul(
                    new ValueFloatVector2fY(movement),
                    new ValueFloatStruct(0.5f))));

        BodyPart forearmL = new DefaultBodyPart(
            new RectfStruct(0.6f, 0.5f, 0.2f, 0.5f),
            new RectfStruct(-0.5f, -0.8f, 1f, 1f),
            new Matrix3fTransform(
                new Vector2fStruct(0f, -1f),
                new ValueFloatSub(
                    sin,
                    new ValueFloatMul(movementLength,
                        new ValueFloatStruct(6f)))));

        BodyPart forearmR = new DefaultBodyPart(
            new RectfStruct(0.6f, 0.5f, 0.2f, 0.5f),
            new RectfStruct(-0.5f, -0.8f, 1f, 1f),
            new Matrix3fTransform(
                new Vector2fStruct(0f, -1f),
                new ValueFloatSub(
                    new ValueFloatNegative(sin),
                    new ValueFloatMul(movementLength,
                        new ValueFloatStruct(6f)))));

        BodyPart armL = new DefaultBodyPart(
            new RectfStruct(0.6f, 0f, 0.2f, 0.5f),
            new RectfStruct(-0.5f, -1f, 1f, 1f),
            new Matrix3fTransform(
                new Vector2fStruct(-0.3f, 0.5f),
                new Vector2fStruct(0.5f, 0.5f),
                sin),
            forearmL);

        BodyPart armR = new DefaultBodyPart(
            new RectfStruct(0.6f, 0f, 0.2f, 0.5f),
            new RectfStruct(-0.5f, -1f, 1f, 1f),
            new Matrix3fTransform(
                new Vector2fStruct(0.1f, 0.5f),
                new Vector2fStruct(0.5f, 0.5f),
                new ValueFloatNegative(sin)),
            forearmR);

        BodyPart shoe = new DefaultBodyPart(
            new RectfStruct(0.8f, 0.5f, 0.2f, 0.5f),
            new RectfStruct(-0.5f, -0.8f, 1f, 1f),
            new Matrix3fTranslation(new Vector2fStruct(0f, -0.5f)));
        
        BodyPart kneeL = new DefaultBodyPart(
            new RectfStruct(0.4f, 0.5f, 0.2f, 0.5f),
            new RectfStruct(-0.5f, -0.8f, 1f, 1f),
            new Matrix3fTransform(new Vector2fStruct(0f, -0.8f),
                new ValueFloatAdd(new ValueFloatNegative(sin),
                    new ValueFloatMul(movementLength,
                        new ValueFloatStruct(6f)))),
            shoe);

        BodyPart kneeR = new DefaultBodyPart(
            new RectfStruct(0.4f, 0.5f, 0.2f, 0.5f),
            new RectfStruct(-0.5f, -0.8f, 1f, 1f),
            new Matrix3fTransform(new Vector2fStruct(0f, -0.8f),
                new ValueFloatAdd(sin,
                    new ValueFloatMul(movementLength,
                        new ValueFloatStruct(6f)))),
            shoe);

        BodyPart legL = new DefaultBodyPart(
            new RectfStruct(0.4f, 0f, 0.2f, 0.5f),
            new RectfStruct(-0.5f, -0.8f, 1f, 1.3f),
            new Matrix3fTransform(
                new Vector2fStruct(-0.15f, -0.45f),
                new Vector2fStruct(0.5f, 0.5f),
                new ValueFloatNegative(sin)),
            kneeL);

        BodyPart legR = new DefaultBodyPart(
            new RectfStruct(0.4f, 0f, 0.2f, 0.5f),
            new RectfStruct(-0.5f, -0.8f, 1f, 1.3f),
            new Matrix3fTransform(
                new Vector2fStruct(0.1f, -0.45f),
                new Vector2fStruct(0.5f, 0.5f),
                sin),
            kneeR);

        BodyPart body = new DefaultBodyPart(
            new RectfStruct(0f, 0f, 0.4f, 1f),
            new RectfStruct(-0.5f, -0.5f, 0.8f, 1.2f),
            Matrix3fIdentity.identity,
            head);
        
        Matrix3f transform = new Matrix3fMul(
            new Matrix3fTranslation(
                new Vector2fAdd(
                    position,
                    new Vector2fStruct(0f, 1.25f))),
            new Matrix3fScale(
                new Vector2fValueFloat(
                    dir,
                    new ValueFloatStruct(1f))));

        root = new BodyPart()
        {
            @Override
            public GraphicsPainter paint(GraphicsBrush clip)
            {
                return new MatrixScopeGraphicsPainter(transform,
                    new CompositeGraphicsBrushPainter(
                        armR,
                        legR,
                        body,
                        legL,
                        armL
                    ).paint(clip));
            }

            @Override
            public GraphicsPainter inspect(GraphicsInspector inspector)
            {
                return new MatrixScopeGraphicsPainter(transform, new CompositeGraphicsPainter(
                    new CachedIterable<>(
                        new MapIterable<>(
                            new ArrayIterable<>(armR, legR, body, legL, armL),
                            i -> i.inspect(inspector)))));
            }
        };
    }

    @Override
    public GraphicsPainter paint(GraphicsBrush clip)
    {
        return root.paint(clip);
    }

    @Override
    public GraphicsPainter inspect(GraphicsInspector inspector)
    {
        return new NoneGraphicsPainter();// root.inspect(inspector);
    }
}