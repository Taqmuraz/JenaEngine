package jena.opengl.texture;

import jena.engine.common.Action;
import jena.opengl.OpenGLTexture;
import jena.opengl.OpenGLTextureFunctions;

public class OpenGLPointFilterTexture implements OpenGLTexture
{
    OpenGLTexture texture;
    OpenGLTextureFunctions gl;

    public OpenGLPointFilterTexture(OpenGLTextureFunctions gl, OpenGLTexture texture)
    {
        this.gl = gl;
        this.texture = texture;
    }

    @Override
    public void bind(Action action)
    {
        texture.bind(() ->
        {
            gl.disableFilter();
            action.call();
        });
    }
}