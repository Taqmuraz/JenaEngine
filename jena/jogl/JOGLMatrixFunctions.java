package jena.jogl;

import jena.engine.math.Matrix3f;
import jena.opengl.OpenGLMatrixFunctions;

public class JOGLMatrixFunctions implements OpenGLMatrixFunctions
{
    JOGL_ES1_Provider gl;

    public JOGLMatrixFunctions(JOGL_ES1_Provider gl)
    {
        this.gl = gl;
    }
    @Override
    public void push()
    {
        gl.gl().glPushMatrix();
    }
    @Override
    public void pop()
    {
        gl.gl().glPopMatrix();
    }
    @Override
    public void identity()
    {
        gl.gl().glLoadIdentity();
    }
    @Override
    public void viewport(int x, int y, int width, int height)
    {
        gl.gl().glViewport(x, y, width, height);
    }
    @Override
    public void mult(Matrix3f matrix)
    {
        matrix.accept(elements ->
        {
            gl.gl().glMultMatrixf(new float[] 
            {
                elements.at(0), elements.at(1), 0f, 0f,
                elements.at(3), elements.at(4), 0f, 0f,
                0f, 0f, 0f, 0f,
                elements.at(6), elements.at(7), 0f, 1f
            }, 0);
        });
    }
}