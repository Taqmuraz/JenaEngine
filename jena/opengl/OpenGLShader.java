package jena.opengl;

import jena.engine.common.Action;

public interface OpenGLShader extends OpenGLUniforms
{
    void play(Action action);
}