package jena.engine.math;

public final class Vector2fNormalized implements Vector2f
{
    Vector2f source;

    public Vector2fNormalized(Vector2f source)
    {
        this.source = source;
    }
    @Override
    public void accept(Vector2fAcceptor acceptor)
    {
        Vector2fStruct struct = new Vector2fStruct(source);
        float length = struct.length();
        if (length != 0)
            acceptor.call(struct.x / length, struct.y / length);
        else
            acceptor.call(0f, 0f);
    }
}