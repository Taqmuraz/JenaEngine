package jena.opengl.gles;

import com.jogamp.opengl.GL2ES1;

import jena.engine.common.Action;
import jena.engine.graphics.Transformation;
import jena.engine.math.Matrix3fElements;
import jena.engine.math.Matrix3fIdentity;
import jena.engine.math.Matrix3fMul;
import jena.engine.math.Matrix3fRect;
import jena.engine.math.Matrix3fStack;
import jena.engine.math.Rectf;
import jena.opengl.OpenGLMatrixPipeline;

public class OpenGLESMatrixPipeline implements OpenGLMatrixPipeline
{
    GL2ES1 gl;
    Matrix3fStack stack;

    public OpenGLESMatrixPipeline(GL2ES1 gl)
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
            matrixScope(s -> new Matrix3fMul(s, new Matrix3fRect(a -> a.call(0f, 0f, w, h))), action);
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