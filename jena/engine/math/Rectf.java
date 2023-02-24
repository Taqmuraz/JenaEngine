package jena.engine.math;

public interface Rectf
{
    void accept(RectfAcceptor acceptor);

    default Rectf normalized()
    {
        return new RectfNormalized(this);
    }
}