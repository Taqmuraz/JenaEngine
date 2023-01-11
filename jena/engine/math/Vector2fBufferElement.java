package jena.engine.math;

public final class Vector2fBufferElement implements Vector2f
{
    float[] buffer;
    int startIndex;
    int stride;

    public Vector2fBufferElement(float[] buffer, int startIndex, int stride)
    {
        this.buffer = buffer;
        this.startIndex = startIndex;
        this.stride = stride;
    }

    @Override
    public void accept(Vector2fAcceptor acceptor)
    {
        acceptor.call(buffer[startIndex], buffer[startIndex + stride]);
    }
}