package jena.opengl.gles;

import jena.engine.common.Action;
import jena.engine.graphics.Transformation;
import jena.engine.math.Matrix3fAcceptor;
import jena.engine.math.Matrix3fIdentity;
import jena.engine.math.Matrix3fMul;
import jena.engine.math.Matrix3fRect;
import jena.engine.math.Matrix3fStack;
import jena.engine.math.Rectf;
import jena.opengl.OpenGLMatrixFunctions;
import jena.opengl.OpenGLMatrixPipeline;

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
    public void accept(Matrix3fAcceptor acceptor)
    {
        stack.accept(acceptor);
    }

    @Override
    public void matrixScope(Transformation transformation, Action action)
    {
        stack.matrixScope(transformation, () ->
        {
            gl.push();
            gl.mult(transformation.transform(Matrix3fIdentity.identity));
            action.call();
            gl.pop();
        });
    }

    public void rectScope(Rectf rect, Action action)
    {
        rect.accept((x, y, w, h) ->
        {
            gl.push();
            gl.identity();
            gl.viewport((int)x, (int)y, (int)w, (int)h);
            matrixScope(s -> new Matrix3fMul(s, new Matrix3fRect(a -> a.call(-1f, 1f, 2f / w, -2f / h))), action);
            gl.pop();
        });
    }
}