package jena.opengl.primitive;

import jena.engine.math.Rectf;
import jena.opengl.OpenGLPrimitive;
import jena.opengl.OpenGLUniforms;

public class OpenGLRectPrimitive implements OpenGLPrimitive
{
    OpenGLPrimitive primitive;
    Rectf uv;
    OpenGLUniforms uniforms;

    public OpenGLRectPrimitive(OpenGLPrimitive primitive, Rectf rect, OpenGLUniforms uniforms)
    {
        this.primitive = primitive;
        this.uv = rect;
        this.uniforms = uniforms;
    }

    @Override
    public void draw()
    {
        uniforms.loadUniformRect("rect", uv);
        primitive.draw();
    }
}