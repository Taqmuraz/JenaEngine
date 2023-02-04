package jena.opengl.texture;

import jena.engine.common.Action;
import jena.opengl.OpenGLTexture;
import jena.opengl.OpenGLTextureFunctions;

public class OpenGLRepeatedTexture implements OpenGLTexture
{
    OpenGLTexture texture;

    public OpenGLRepeatedTexture(OpenGLTexture texture)
    {
        this.texture = texture;
    }

    @Override
    public void bind(OpenGLTextureFunctions gl, Action action)
    {
        texture.bind(gl, () ->
        {
            gl.repeat();
            action.call();
        });
    }
}