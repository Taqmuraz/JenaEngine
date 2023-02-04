package jena.opengl;

public interface OpenGLVertexBufferContext
{
    void data(float[] data);
    void floatAttribPointer(int index, int stride);
}