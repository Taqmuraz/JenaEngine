package jena.opengl.primitive;

import jena.engine.math.Matrix3f;
import jena.opengl.OpenGLPrimitive;
import jena.opengl.OpenGLUniforms;

public class OpenGLTransformedPrimitive implements OpenGLPrimitive
{
    OpenGLPrimitive primitive;
    Matrix3f matrix;
    OpenGLUniforms uniforms;

    public OpenGLTransformedPrimitive(OpenGLPrimitive primitive, Matrix3f matrix, OpenGLUniforms uniforms)
    {
        this.primitive = primitive;
        this.matrix = matrix;
        this.uniforms = uniforms;
    }

    @Override
    public void draw()
    {
        uniforms.loadUniformMatrix("transform", matrix);
        primitive.draw();
    }
}