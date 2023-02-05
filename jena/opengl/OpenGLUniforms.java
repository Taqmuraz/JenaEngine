package jena.opengl;

import jena.engine.graphics.Color;
import jena.engine.math.Matrix3f;
import jena.engine.math.Rectf;

public interface OpenGLUniforms
{
    void loadUniformMatrix(String name, Matrix3f matrix);
    void loadUniformRect(String name, Rectf rect);
    void loadUniformColor(String name, Color color);
}