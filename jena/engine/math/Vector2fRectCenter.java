package jena.engine.math;

public class Vector2fRectCenter implements Vector2f
{
    Rectf rect;

    public Vector2fRectCenter(Rectf rect)
    {
        this.rect = rect;
    }

    @Override
    public void accept(Vector2fAcceptor acceptor)
    {
        rect.accept((x, y, w, h) -> acceptor.call(x + w * 0.5f, y + h * 0.5f));
    }
}