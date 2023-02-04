package jena.opengl.primitive;

import jena.opengl.OpenGLFunctions;
import jena.opengl.OpenGLPrimitive;
import jena.opengl.OpenGLTexture;

public class OpenGLTexturedPrimitive implements OpenGLPrimitive
{
    private OpenGLTexture texture;
    private OpenGLPrimitive primitive;
    private OpenGLFunctions gl;

    public OpenGLTexturedPrimitive(OpenGLPrimitive primitive, OpenGLTexture texture, OpenGLFunctions gl)
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