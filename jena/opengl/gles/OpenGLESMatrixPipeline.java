package jena.opengl.gles;

import com.jogamp.opengl.GLES3;

import jena.engine.common.Action;
import jena.engine.graphics.Transformation;
import jena.engine.math.Matrix3fElements;
import jena.engine.math.Matrix3fMul;
import jena.engine.math.Matrix3fRect;
import jena.engine.math.Matrix3fStack;
import jena.engine.math.Rectf;
import jena.opengl.OpenGLMatrixPipeline;

public class OpenGLESMatrixPipeline implements OpenGLMatrixPipeline
{
    GLES3 gl;
    Matrix3fStack stack;

    public OpenGLESMatrixPipeline(GLES3 gl)
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
        stack.matrixScope(transformation, action);
    }

    public void rectScope(Rectf rect, Action action)
    {
        rect.accept((x, y, w, h) ->
        {
            gl.glViewport((int)x, (int)y, (int)w, (int)h);
            matrixScope(s -> new Matrix3fMul(s, new Matrix3fRect(a -> a.call(-1f, 1f, 2f / w, -2f / h))), action);
        });
    }
}