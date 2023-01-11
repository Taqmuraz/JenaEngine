package jena.engine.math;

public final class Vector3fBufferElement implements Vector3f
{
    float[] buffer;
    int startIndex;
    int stride;
    public Vector3fBufferElement(float[] buffer, int startIndex, int stride)
    {
        this.buffer = buffer;
        this.startIndex = startIndex;
        this.stride = stride;
    }

    @Override
    public void accept(Vector3fAcceptor acceptor)
    {
        acceptor.call(
            buffer[startIndex],
            buffer[startIndex + stride],
            buffer[startIndex + stride + stride]);
    }
}