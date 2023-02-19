package jena.engine.math;

public class Vector2fClampRect implements Vector2f
{
    private Vector2f result;

    public Vector2fClampRect(Vector2f source, Rectf rect)
    {
        result = a -> source.accept((sx, sy) -> rect.accept((x, y, w, h) ->
        {
            float rx = sx;
            float ry = sy;
            if (rx < x) rx = x;
            else if (rx > (x + w)) rx = x + w;
            if (ry < y) ry = y;
            else if (ry > (y + h)) ry = y + h;
            a.call(rx, ry);
        }));
    }

    @Override
    public void accept(Vector2fAcceptor acceptor)
    {
        result.accept(acceptor);
    }
}