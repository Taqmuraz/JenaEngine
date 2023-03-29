package jena.engine.math;

public final class RectfLocationSize implements Rectf
{
    private Vector2f location;
    private Vector2f size;

    public RectfLocationSize(Vector2f location, Vector2f size)
    {
        this.location = location;
        this.size = size;
    }

    @Override
    public void accept(RectfAcceptor acceptor)
    {
        location.accept((x, y) -> size.accept((w, h) -> acceptor.call(x, y, w, h)));
    }
}