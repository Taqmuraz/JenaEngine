package jena.engine.entity.human;

import jena.engine.entity.FrameEndListener;
import jena.engine.entity.FrameStartListener;
import jena.engine.entity.Time;
import jena.engine.graphics.GraphicsClip;
import jena.engine.graphics.GraphicsClipPainter;
import jena.engine.graphics.GraphicsResource;
import jena.engine.graphics.TextureHandle;
import jena.engine.input.Key;
import jena.engine.input.Keyboard;
import jena.engine.io.StorageFileResource;
import jena.engine.math.FloatAcceptor;
import jena.engine.math.Matrix3fBuilder;
import jena.engine.math.ValueFloat;
import jena.engine.math.Vector2f;
import jena.engine.math.Vector2fAcceptor;

public class Player implements GraphicsClipPainter, FrameStartListener, FrameEndListener
{
    Human human;
    TextureHandle groundTexture;
    TextureHandle skyTexture;

    public Player(GraphicsResource graphicsResource, Keyboard keyboard)
    {
        human = new Human(graphicsResource, () -> new Vector2f()
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
        });
        groundTexture = graphicsResource.loadTexture(new StorageFileResource("Ground.png"));
        skyTexture = graphicsResource.loadTexture(new StorageFileResource("Sky.png"));
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
        float start = Time.time();

        public BackgroundOffset(float max, float speed)
        {
            this.max = max;
            this.speed = speed;
        }

        @Override
        public void accept(FloatAcceptor acceptor)
        {
            float time = Time.time();
            if ((time - start) * speed >= max) start = time;
            acceptor.call((time - start) * speed);
        }
    }

    @Override
    public void paint(GraphicsClip clip)
    {
        ValueFloat groundOffset = new BackgroundOffset(10f, 0f);
        ValueFloat skyOffset = new BackgroundOffset(40f, 0f);

        clip.matrixScope(s -> new Matrix3fBuilder(s).translate(a -> a.call(1f, -2f)).build(), () ->
        {
            clip.drawTile(skyTexture, a -> a.call(2f, 1f), a -> skyOffset.accept(sky -> a.call(-20f - sky, 0f, 60f, 5f)));
            clip.drawTile(groundTexture, a -> a.call(3f, 1f), a -> groundOffset.accept(ground -> a.call(-10f - ground, -8f, 30f, 8f)));
            human.paint(clip);
        });
    }
}