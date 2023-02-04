package jena.opengl;

import jena.engine.common.ActionSingle;

public interface OpenGLVertexBuffer
{
    void bind(ActionSingle<OpenGLVertexBufferContext> action);
}