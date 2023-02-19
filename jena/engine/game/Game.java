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
import jena.engine.math.FloatAcceptor;
import jena.engine.math.Matrix3fBuilder;
import jena.engine.math.MultipleFieldVector2f;
import jena.engine.math.RectFieldVector2f;
import jena.engine.math.Rectf;
import jena.engine.math.ValueFloat;
import jena.engine.math.Vector2f;

public class Game implements GraphicsClipPainter, FrameStartListener, FrameEndListener
{
    Human human;
    TextureHandle groundTexture;
    TextureHandle skyTexture;
    Rectf skyRect;
    Rectf groundRect;

    public Game(GraphicsResource graphicsResource, Storage storage, Controller controller)
    {
        groundTexture = graphicsResource.loadTexture(storage.open("Ground.png"));
        skyTexture = graphicsResource.loadTexture(storage.open("Sky.png"));

        ValueFloat groundOffset = new BackgroundOffset(10f, 0f);
        ValueFloat skyOffset = new BackgroundOffset(40f, 0f);
        skyRect = a -> skyOffset.accept(sky -> a.call(-20f - sky, 0f, 60f, 5f));
        groundRect = a -> groundOffset.accept(ground -> a.call(-10f - ground, -8f, 30f, 8f));

        human = new Human(graphicsResource, storage, controller, new MultipleFieldVector2f(
            new RectFieldVector2f(skyRect),
            new RectFieldVector2f(groundRect)));
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
        clip.matrixScope(s -> new Matrix3fBuilder(s).translate(a -> a.call(0f, -2f)).build(), () ->
        {
            clip.drawTile(skyTexture, a -> a.call(2f, 1f), skyRect);
            clip.drawTile(groundTexture, a -> a.call(3f, 1f), groundRect);
            human.paint(clip);
        });
    }

    public Vector2f position()
    {
        return human.position();
    }
}