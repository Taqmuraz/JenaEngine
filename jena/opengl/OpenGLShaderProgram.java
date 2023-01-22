package jena.opengl;

import jena.engine.common.Action;

public interface OpenGLShaderProgram extends OpenGLUniforms
{
    void execute(Action action);
}