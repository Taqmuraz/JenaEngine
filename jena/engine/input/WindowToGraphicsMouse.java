package jena.engine.input;

import jena.engine.math.Rectf;
import jena.engine.math.Vector2f;

public class WindowToGraphicsMouse implements Mouse
{
    Mouse mouse;
    Vector2f graphicsSize;
    Rectf graphicsRect;

    public WindowToGraphicsMouse(Mouse mouse, Vector2f graphicsSize, Rectf graphicsRect)
    {
        this.mouse = mouse;
        this.graphicsSize = graphicsSize;
        this.graphicsRect = graphicsRect;
    }

    @Override
    public Vector2f position()
    {
        return r -> graphicsSize.accept((gw, gh) -> graphicsRect.accept((rx, ry, rw, rh) -> mouse.position().accept((x, y) -> 
        {
            r.call((x - rx) / rw * gw, (y - ry) / rh * gh);
        })));
    }

    @Override
    public Key button(int button)
    {
        return mouse.button(button);
    }
}