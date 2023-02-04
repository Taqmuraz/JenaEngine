package jena.opengl;

import jena.engine.common.ActionSingle;

public interface OpenGLVertexArray
{
    void bind(ActionSingle<OpenGLVertexArrayContext> action);
    void drawTriangles();
}