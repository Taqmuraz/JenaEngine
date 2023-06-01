package jena.engine.math;

public final class Vector2fNormalized implements Vector2f
{
    Vector2f source;
    ValueFloat length;

    public Vector2fNormalized(Vector2f source)
    {
        this.source = source;
        length = new Vector2fLength(source);
    }
    @Override
    public void accept(Vector2fAcceptor acceptor)
    {
        length.accept(len -> source.accept((x, y) ->
        {
            if (len != 0)
                acceptor.call(x / len, y / len);
            else
                acceptor.call(0f, 0f);
        }));
    }
}