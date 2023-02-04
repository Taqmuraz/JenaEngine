package jena.opengl.texture;

import jena.engine.common.Action;
import jena.opengl.OpenGLTexture;
import jena.opengl.OpenGLTextureFunctions;

public class OpenGLPointFilterTexture implements OpenGLTexture
{
    OpenGLTexture texture;

    public OpenGLPointFilterTexture(OpenGLTexture texture)
    {
        this.texture = texture;
    }

    @Override
    public void bind(OpenGLTextureFunctions gl, Action action)
    {
        texture.bind(gl, () ->
        {
            gl.disableFilter();
            action.call();
        });
    }
}