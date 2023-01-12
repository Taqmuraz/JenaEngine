package jena.engine.input;

import jena.engine.math.Rectf;
import jena.engine.math.Vector2f;

public class ClipMouse implements Mouse
{
    Rectf clip;
    Mouse mouse;

    public ClipMouse(Rectf clip, Mouse mouse)
    {
        this.clip = clip;
        this.mouse = mouse;
    }

    @Override
    public Vector2f position()
    {
        return a -> clip.accept((x, y, w, h) -> mouse.position().accept((px, py) ->
        {
            px -= x;
            py -= y;
            if (px < 0f) px = 0f;
            if (px > w) px = w;
            if (py < 0f) py = 0f;
            if (py > h) py = h;
            a.call(px, py);
        }));
    }

    @Override
    public Key button(int button)
    {
        return mouse.button(button);
    }
}