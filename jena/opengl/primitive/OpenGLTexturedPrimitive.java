package jena.opengl.primitive;

import com.jogamp.opengl.GL;

import jena.opengl.OpenGLPrimitive;
import jena.opengl.OpenGLTexture;

public class OpenGLTexturedPrimitive implements OpenGLPrimitive
{
    private OpenGLTexture texture;
    private OpenGLPrimitive primitive;
    private GL gl;

    public OpenGLTexturedPrimitive(OpenGLPrimitive primitive, OpenGLTexture texture, GL gl)
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