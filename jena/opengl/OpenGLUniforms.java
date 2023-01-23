package jena.opengl;

import jena.engine.math.Matrix3f;
import jena.engine.math.Rectf;

public interface OpenGLUniforms
{
    void loadUniformMatrix(String name, Matrix3f matrix);
    void loadUniformRect(String name, Rectf rect);
}