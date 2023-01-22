package jena.opengl;

import jena.engine.math.Matrix3f;

public interface OpenGLUniforms
{
    void loadUniformMatrix(String name, Matrix3f matrix);
}