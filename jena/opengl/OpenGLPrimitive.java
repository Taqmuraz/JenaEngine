package jena.opengl;

import com.jogamp.opengl.GL;

import jena.engine.math.Matrix3f;
import jena.opengl.primitive.OpenGLTransformedPrimitive;
import jena.opengl.primitive.OpenGLTexturedPrimitive;

public interface OpenGLPrimitive
{
    void draw();

    default OpenGLPrimitive textured(OpenGLTexture texture, GL gl)
    {
        return new OpenGLTexturedPrimitive(this, texture, gl);
    }
    default OpenGLPrimitive transformed(Matrix3f matrix, OpenGLUniforms uniforms)
    {
        return new OpenGLTransformedPrimitive(this, matrix, uniforms);
    }
}