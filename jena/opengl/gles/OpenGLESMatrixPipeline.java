package jena.opengl.gles;

import jena.engine.common.Action;
import jena.engine.math.Matrix3f;
import jena.engine.math.Matrix3fPipeline;
import jena.engine.math.Matrix3fStack;
import jena.opengl.OpenGLMatrixFunctions;

public class OpenGLESMatrixPipeline implements Matrix3fPipeline
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
}