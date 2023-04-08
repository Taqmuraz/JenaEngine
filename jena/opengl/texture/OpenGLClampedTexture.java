package jena.opengl.texture;

import jena.engine.common.Action;
import jena.opengl.OpenGLTexture;
import jena.opengl.OpenGLTextureFunctions;

public class OpenGLClampedTexture implements OpenGLTexture
{
    OpenGLTexture texture;
    OpenGLTextureFunctions gl;

    public OpenGLClampedTexture(OpenGLTextureFunctions gl, OpenGLTexture texture)
    {
        this.gl = gl;
        this.texture = texture;
    }

    @Override
    public void bind(Action action)
    {
        texture.bind(() ->
        {
            gl.clamp();
            action.call();
        });
    }
}