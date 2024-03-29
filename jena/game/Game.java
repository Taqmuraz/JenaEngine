package jena.game;

import jena.editor.GraphicsInspectable;
import jena.editor.GraphicsInspector;
import jena.engine.graphics.CompositeGraphicsDrawing;
import jena.engine.graphics.CompositeGraphicsPainter;
import jena.engine.graphics.GraphicsBrush;
import jena.engine.graphics.GraphicsBrushPainter;
import jena.engine.graphics.GraphicsDrawingPainter;
import jena.engine.graphics.GraphicsPainter;
import jena.engine.graphics.GraphicsResource;
import jena.engine.graphics.MatrixScopeGraphicsPainter;
import jena.engine.graphics.TextureHandle;
import jena.engine.io.Storage;
import jena.engine.math.FieldVector2f;
import jena.engine.math.FloatAcceptor;
import jena.engine.math.Matrix3fTranslation;
import jena.engine.math.MultipleClampFieldVector2f;
import jena.engine.math.RectClampFieldVector2f;
import jena.engine.math.RectPunctureFieldVector2f;
import jena.engine.math.Rectf;
import jena.engine.math.ValueFloat;
import jena.engine.math.Vector2f;
import jena.engine.math.Vector2fAdd;
import jena.engine.math.Vector2fNormalized;
import jena.engine.math.Vector2fStruct;
import jena.engine.math.Vector2fZero;
import jena.game.human.Human;
import jena.game.human.InputPosition;

public class Game implements GraphicsBrushPainter, FrameStartListener, FrameEndListener, GraphicsInspectable
{
    Human human;
    InputPosition position;
    TextureHandle groundTexture;
    TextureHandle skyTexture;
    Rectf skyRect;
    Rectf groundRect;
    Rectf obstacleRect;

    public Game(GraphicsResource graphicsResource, Storage storage, Controller controller)
    {
        groundTexture = graphicsResource.loadTexture(storage.open("Ground.png"));
        skyTexture = graphicsResource.loadTexture(storage.open("Sky.png"));

        ValueFloat groundOffset = new BackgroundOffset(10f, 0f);
        ValueFloat skyOffset = new BackgroundOffset(40f, 0f);
        ValueFloat time = new Time();
        skyRect = a -> skyOffset.accept(sky -> a.call(-20f - sky, 0f, 60f, 5f));
        groundRect = a -> groundOffset.accept(ground -> a.call(-10f - ground, -8f, 30f, 8f));
        obstacleRect = a -> time.accept(t -> a.call(-4f + (float)Math.sin(t) * 2f, -5f + (float)Math.cos(t) * 2f, 8f, 2f));

        FieldVector2f clampField = new MultipleClampFieldVector2f(
            new RectClampFieldVector2f(skyRect),
            new RectClampFieldVector2f(groundRect));
        FieldVector2f punctureField = new RectPunctureFieldVector2f(obstacleRect);


        Vector2f movement = new Vector2fNormalized(controller.movement());
        position = new InputPosition(movement, new Vector2fZero(), source ->
        {
            return punctureField.project(clampField.project(source));
        });

        human = new Human(graphicsResource, storage, position, movement);
    }

    @Override
    public void onStartFrame()
    {
        position.onStartFrame();
    }
    @Override
    public void onEndFrame()
    {
    }

    class BackgroundOffset implements ValueFloat
    {
        float max;
        float speed;
        float start;
        ValueFloat time;

        public BackgroundOffset(float max, float speed)
        {
            this.max = max;
            this.speed = speed;
            time = new Time();
            time.accept(time -> start = time);
        }

        @Override
        public void accept(FloatAcceptor acceptor)
        {
            time.accept(time ->
            {
                if ((time - start) * speed >= max) start = time;
                acceptor.call((time - start) * speed);
            });
        }
    }

    @Override
    public GraphicsPainter paint(GraphicsBrush clip)
    {
        return new CompositeGraphicsPainter(
            new GraphicsDrawingPainter(
                new CompositeGraphicsDrawing(
                    clip.drawTile(skyTexture, a -> a.call(2f, 1f), skyRect),
                    clip.drawTile(groundTexture, a -> a.call(3f, 1f), groundRect),
                    clip.fillRect(obstacleRect, a -> a.call(255, 0, 0, 255)))),
            human.paint(clip),
            new MatrixScopeGraphicsPainter(
                new Matrix3fTranslation(position.add(new Vector2fStruct(0f, 4f))).scale(new Vector2fStruct(2f, -2f)),
                new FPSBrushPainter().paint(clip)));
    }

    public Vector2f position()
    {
        return new Vector2fAdd(position, a -> a.call(0f, 2f));
    }

    @Override
    public GraphicsPainter inspect(GraphicsInspector inspector)
    {
        return human.inspect(inspector);
    }
}