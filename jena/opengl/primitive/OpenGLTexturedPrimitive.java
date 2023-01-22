package jena.opengl.primitive;

import com.jogamp.opengl.GL;

import jena.opengl.OpenGLDiffuseTexture;
import jena.opengl.OpenGLPrimitive;

public class OpenGLTexturedPrimitive implements OpenGLPrimitive
{
    private OpenGLDiffuseTexture texture;
    private OpenGLPrimitive primitive;
    private GL gl;

    public OpenGLTexturedPrimitive(OpenGLPrimitive primitive, OpenGLDiffuseTexture texture, GL gl)
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