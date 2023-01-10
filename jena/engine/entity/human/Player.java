package jena.engine.entity.human;

import jena.engine.entity.FrameEndHandler;
import jena.engine.entity.FrameStartHandler;
import jena.engine.graphics.GraphicsClip;
import jena.engine.graphics.GraphicsClipPainter;
import jena.engine.graphics.GraphicsResource;
import jena.engine.input.Key;
import jena.engine.input.Keyboard;
import jena.engine.math.Vector2f;
import jena.engine.math.Vector2fAcceptor;

public class Player implements GraphicsClipPainter, FrameStartHandler, FrameEndHandler
{
    Human human;

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
        human.paint(clip);
    }
}