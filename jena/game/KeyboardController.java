package jena.game;

import jena.engine.input.Key;
import jena.engine.input.Keyboard;
import jena.engine.math.Vector2f;
import jena.engine.math.Vector2fAcceptor;

public class KeyboardController implements Controller
{
    Vector2f movement;

    public KeyboardController(Keyboard keyboard)
    {
        movement = new Vector2f() 
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
    }

    @Override
    public Vector2f movement()
    {
        return movement;
    }
}