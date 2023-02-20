package jena.engine.game;

import jena.engine.entity.Controller;
import jena.engine.entity.FrameEndListener;
import jena.engine.entity.FrameStartListener;
import jena.engine.entity.Time;
import jena.engine.entity.human.Human;
import jena.engine.graphics.GraphicsClip;
import jena.engine.graphics.GraphicsClipPainter;
import jena.engine.graphics.GraphicsResource;
import jena.engine.graphics.TextureHandle;
import jena.engine.io.Storage;
import jena.engine.math.FieldVector2f;
import jena.engine.math.FloatAcceptor;
import jena.engine.math.MultipleClampFieldVector2f;
import jena.engine.math.RectClampFieldVector2f;
import jena.engine.math.RectPunctureFieldVector2f;
import jena.engine.math.Rectf;
import jena.engine.math.ValueFloat;
import jena.engine.math.Vector2f;
import jena.engine.math.Vector2fAdd;

public class Game implements GraphicsClipPainter, FrameStartListener, FrameEndListener
{
    Human human;
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
        skyRect = a -> skyOffset.accept(sky -> a.call(-20f - sky, 0f, 60f, 5f));
        groundRect = a -> groundOffset.accept(ground -> a.call(-10f - ground, -8f, 30f, 8f));
        obstacleRect = a -> Time.accept(t -> a.call(-4f + (float)Math.sin(t) * 2f, -5f + (float)Math.cos(t) * 2f, 8f, 2f));

        FieldVector2f clampField = new MultipleClampFieldVector2f(
            new RectClampFieldVector2f(skyRect),
            new RectClampFieldVector2f(groundRect));
        FieldVector2f punctureField = new RectPunctureFieldVector2f(obstacleRect);

        human = new Human(graphicsResource, storage, controller, source ->
        {
            return punctureField.project(clampField.project(source));
        });
    }

    @Override
    public void onStartFrame()
    {
        human.onStartFrame();
    }
    @Override
    public void onEndFrame()
    {
        human.onEndFrame();
    }

    class BackgroundOffset implements ValueFloat
    {
        float max;
        float speed;
        float start;

        public BackgroundOffset(float max, float speed)
        {
            this.max = max;
            this.speed = speed;
            Time.accept(time -> start = time);
        }

        @Override
        public void accept(FloatAcceptor acceptor)
        {
            Time.accept(time ->
            {
                if ((time - start) * speed >= max) start = time;
                acceptor.call((time - start) * speed);
            });
        }
    }

    @Override
    public void paint(GraphicsClip clip)
    {
        clip.drawTile(skyTexture, a -> a.call(2f, 1f), skyRect);
        clip.drawTile(groundTexture, a -> a.call(3f, 1f), groundRect);
        clip.fillRect(obstacleRect, a -> a.call(255, 0, 0, 255));
        human.paint(clip);
    }

    public Vector2f position()
    {
        return new Vector2fAdd(human.position(), a -> a.call(0f, 2f));
    }
}