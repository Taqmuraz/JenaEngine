package jena.opengl.primitive;

import jena.opengl.OpenGLPrimitive;
import jena.opengl.OpenGLTexture;

public class OpenGLTexturedPrimitive implements OpenGLPrimitive
{
    private OpenGLTexture texture;
    private OpenGLPrimitive primitive;

    public OpenGLTexturedPrimitive(OpenGLPrimitive primitive, OpenGLTexture texture)
    {
        this.texture = texture;
        this.primitive = primitive;
    }

    public void draw()
    {
        texture.bind(primitive::draw);
    }
}