package jena.engine.math;

public final class RectfContainsPoint implements ValueBoolean
{
    private Rectf rect;
    private Vector2f point;

    public RectfContainsPoint(Rectf rect, Vector2f point)
    {
        this.rect = rect;
        this.point = point;
    }

    @Override
    public void accept(BooleanAcceptor acceptor)
    {
        rect.accept((x, y, w, h) -> point.accept((px, py) ->
        {
            acceptor.call(px >= x && py >= y && px < (x + w) && py < (y + h));
        }));
    }
}