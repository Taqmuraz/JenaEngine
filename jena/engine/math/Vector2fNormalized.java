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
        length.accept(len ->
        {
            Vector2fStruct struct = new Vector2fStruct(source);
            if (len != 0)
                acceptor.call(struct.x / len, struct.y / len);
            else
                acceptor.call(0f, 0f);
        });
    }
}