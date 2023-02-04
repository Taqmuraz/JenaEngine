package jena.opengl.primitive;

import jena.opengl.OpenGLTextureFunctions;
import jena.opengl.OpenGLPrimitive;
import jena.opengl.OpenGLTexture;

public class OpenGLTexturedPrimitive implements OpenGLPrimitive
{
    private OpenGLTexture texture;
    private OpenGLPrimitive primitive;
    private OpenGLTextureFunctions gl;

    public OpenGLTexturedPrimitive(OpenGLPrimitive primitive, OpenGLTexture texture, OpenGLTextureFunctions gl)
    {
        this.texture = texture;
        this.primitive = primitive;
        this.gl = gl;
    }

    public void draw()
    {
        texture.bind(gl, primitive::draw);
    }
}