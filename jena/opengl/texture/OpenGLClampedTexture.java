package jena.opengl.texture;

import jena.engine.common.Action;
import jena.opengl.OpenGLTexture;
import jena.opengl.OpenGLTextureFunctions;

public class OpenGLClampedTexture implements OpenGLTexture
{
    OpenGLTexture texture;

    public OpenGLClampedTexture(OpenGLTexture texture)
    {
        this.texture = texture;
    }

    @Override
    public void bind(OpenGLTextureFunctions gl, Action action)
    {
        texture.bind(gl, () ->
        {
            gl.clamp();
            action.call();
        });
    }
}