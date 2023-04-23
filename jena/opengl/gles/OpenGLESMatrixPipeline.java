package jena.opengl.gles;

import jena.engine.common.Action;
import jena.engine.math.Matrix3f;
import jena.engine.math.Matrix3fRect;
import jena.engine.math.Matrix3fStack;
import jena.engine.math.Rectf;
import jena.opengl.OpenGLMatrixFunctions;
import jena.opengl.OpenGLMatrixPipeline;
import jena.opengl.OpenGLRectScope;

public class OpenGLESMatrixPipeline implements OpenGLMatrixPipeline
{
    OpenGLMatrixFunctions gl;
    Matrix3fStack stack;

    public OpenGLESMatrixPipeline(OpenGLMatrixFunctions gl)
    {
        this.gl = gl;
        stack = new Matrix3fStack();
    }

    @Override
    public Matrix3f peek()
    {
        return stack.peek();
    }

    @Override
    public void matrixScope(Matrix3f matrix, Action action)
    {
        stack.matrixScope(matrix, () ->
        {
            gl.push();
            gl.identity();
            gl.mult(matrix);
            action.call();
            gl.pop();
        });
    }

    @Override
    public OpenGLRectScope rectScope(Rectf rect)
    {
        Matrix3f matrix = new Matrix3fRect(a -> rect.accept((x, y, w, h) -> a.call(-1f, 1f, 2f / w, -2f / h)));
        return drawing ->
        {
            gl.push();
            gl.identity();
            rect.accept((x, y, w, h) -> gl.viewport((int)x, (int)y, (int)w, (int)h));
            matrixScope(matrix, drawing::draw);
            gl.pop();
        };
    }
}