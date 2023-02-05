package jena.opengl.primitive;

import jena.engine.graphics.Color;
import jena.opengl.OpenGLPrimitive;
import jena.opengl.OpenGLUniforms;

public class OpenGLColorPrimitive implements OpenGLPrimitive
{
    OpenGLPrimitive primitive;
    Color color;
    OpenGLUniforms uniforms;

    public OpenGLColorPrimitive(OpenGLPrimitive primitive, Color color, OpenGLUniforms uniforms)
    {
        this.primitive = primitive;
        this.color = color;
        this.uniforms = uniforms;
    }

    @Override
    public void draw()
    {
        uniforms.loadUniformColor("color", color);
        primitive.draw();
    }
}