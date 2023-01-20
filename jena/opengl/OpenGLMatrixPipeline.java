package jena.opengl;

import com.jogamp.opengl.GL2;

import jena.engine.common.Action;
import jena.engine.graphics.Transformation;
import jena.engine.math.Matrix3fElements;
import jena.engine.math.Matrix3fIdentity;
import jena.engine.math.Matrix3fPipeline;
import jena.engine.math.Matrix3fStack;
import jena.engine.math.Rectf;

public class OpenGLMatrixPipeline implements Matrix3fPipeline
{
    GL2 gl;
    Matrix3fStack stack;

    public OpenGLMatrixPipeline(GL2 gl)
    {
        this.gl = gl;
        stack = new Matrix3fStack();
    }

    @Override
    public Matrix3fElements elements()
    {
        return stack.elements();
    }

    @Override
    public void matrixScope(Transformation transformation, Action action)
    {
        stack.matrixScope(transformation, () ->
        {
            gl.glPushMatrix();
            Matrix3fElements elements = transformation.transform(Matrix3fIdentity.identity).elements();
            gl.glMultMatrixf(new float[] 
            {
                elements.at(0), elements.at(1), 0f, 0f,
                elements.at(3), elements.at(4), 0f, 0f,
                0f, 0f, 0f, 0f,
                elements.at(6), elements.at(7), 0f, 1f
            }, 0);
            action.call();
            gl.glPopMatrix();
        });
    }

    public void rectScope(Rectf rect, Action action)
    {
        rect.accept((x, y, w, h) ->
        {
            gl.glPushMatrix();
            gl.glLoadIdentity();
            gl.glViewport((int)x, (int)y, (int)w, (int)h);
            gl.glOrtho(0, w, h, 0, -1f, 1f);
            action.call();
            gl.glPopMatrix();
        });
    }

    public void identity(Action action)
    {
        gl.glPushMatrix();
        gl.glLoadIdentity();
        action.call();
        gl.glPopMatrix();
    }
}