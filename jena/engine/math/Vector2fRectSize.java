package jena.engine.math;

public class Vector2fRectSize implements Vector2f
{
    Rectf rect;

    public Vector2fRectSize(Rectf rect)
    {
        this.rect = rect;
    }

    @Override
    public void accept(Vector2fAcceptor acceptor)
    {
        rect.accept((x, y, w, h) -> acceptor.call(w, h));
    }
}