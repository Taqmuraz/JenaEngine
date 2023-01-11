package jena.engine.math;

public final class Vector3fNormalized implements Vector3f
{
    Vector3f source;
    public Vector3fNormalized(Vector3f source)
    {
        this.source = source;
    }

    @Override
    public void accept(Vector3fAcceptor acceptor)
    {
        Vector3fStruct struct = new Vector3fStruct(source);
        float length = struct.length();
        if (length != 0)
            acceptor.call(struct.x / length, struct.y / length, struct.z / length);
        else
            acceptor.call(0f, 0f, 0f);
    }
}