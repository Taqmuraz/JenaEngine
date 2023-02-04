package jena.opengl;

import jena.engine.math.Matrix3f;

public interface OpenGLMatrixFunctions
{
    void push();
    void pop();
    void identity();
    void viewport(int x, int y, int width, int height);
    void mult(Matrix3f matrix);
}