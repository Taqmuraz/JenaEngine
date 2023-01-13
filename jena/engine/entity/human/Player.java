package jena.engine.entity.human;

import jena.engine.entity.FrameEndHandler;
import jena.engine.entity.FrameStartHandler;
import jena.engine.entity.Time;
import jena.engine.graphics.GraphicsClip;
import jena.engine.graphics.GraphicsClipPainter;
import jena.engine.graphics.GraphicsResource;
import jena.engine.graphics.TextureHandle;
import jena.engine.input.Key;
import jena.engine.input.Keyboard;
import jena.engine.io.StorageFileResource;
import jena.engine.math.Matrix3fBuilder;
import jena.engine.math.Matrix3fTranslation;
import jena.engine.math.ValueFloat;
import jena.engine.math.Vector2f;
import jena.engine.math.Vector2fAcceptor;

public class Player implements GraphicsClipPainter, FrameStartHandler, FrameEndHandler
{
    Human human;
    TextureHandle groundTexture;

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
                //acceptor.call(x, y);
                acceptor.call(1f, 0f);
            }
        });
        groundTexture = graphicsResource.loadTexture(new StorageFileResource("Ground.png"));
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

    @Override
    public void paint(GraphicsClip clip)
    {
        ValueFloat groundOffset = new ValueFloat()
        {
            float start = Time.time();

            @Override
            public float read()
            {
                float time = Time.time();
                if (time - start >= 10f) start = time;
                return time - start;
            }
        };
        clip.drawSprite(groundTexture, a -> a.call(0f, 0f, 1f, 1f), a -> a.call(-10f - groundOffset.read(), -10f, 10f, 8f));
        clip.drawSprite(groundTexture, a -> a.call(0f, 0f, 1f, 1f), a -> a.call(-groundOffset.read(), -10f, 10f, 8f));
        clip.drawSprite(groundTexture, a -> a.call(0f, 0f, 1f, 1f), a -> a.call(10f - groundOffset.read(), -10f, 10f, 8f));
        clip.matrixScope(s -> new Matrix3fBuilder(s).translate(a -> a.call(1f, -2f)).build(), () -> human.paint(clip));
    }
}