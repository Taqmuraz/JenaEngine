package jena.opengl.texture;

import com.jogamp.opengl.GL;

import jena.engine.common.Action;
import jena.opengl.OpenGLTexture;

public class OpenGLPointFilterTexture implements OpenGLTexture
{
    OpenGLTexture texture;

    public OpenGLPointFilterTexture(OpenGLTexture texture)
    {
        this.texture = texture;
    }

    @Override
    public void bind(GL gl, Action action)
    {
        texture.bind(gl, () ->
        {
            gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MIN_FILTER, GL.GL_NEAREST);
            gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MAG_FILTER, GL.GL_NEAREST);
            action.call();
        });
    }
}