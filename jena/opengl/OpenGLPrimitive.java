package jena.opengl;

import jena.engine.graphics.Color;
import jena.engine.math.Matrix3f;
import jena.engine.math.Rectf;
import jena.opengl.primitive.OpenGLTransformedPrimitive;
import jena.opengl.primitive.OpenGLColorPrimitive;
import jena.opengl.primitive.OpenGLRectPrimitive;
import jena.opengl.primitive.OpenGLTexturedPrimitive;

public interface OpenGLPrimitive
{
    void draw();

    default OpenGLPrimitive textured(OpenGLTexture texture)
    {
        return new OpenGLTexturedPrimitive(this, texture);
    }
    default OpenGLPrimitive transformed(Matrix3f matrix, OpenGLUniforms uniforms)
    {
        return new OpenGLTransformedPrimitive(this, matrix, uniforms);
    }
    default OpenGLPrimitive rect(Rectf rect, OpenGLUniforms uniforms)
    {
        return new OpenGLRectPrimitive(this, rect, uniforms);
    }
    default OpenGLPrimitive color(Color color, OpenGLUniforms uniforms)
    {
        return new OpenGLColorPrimitive(this, color, uniforms);
    }
}