package jena.opengl.texture;

import com.jogamp.opengl.GL;

import jena.engine.common.Action;
import jena.opengl.OpenGLTexture;

public class OpenGLClampedTexture implements OpenGLTexture
{
    OpenGLTexture texture;

    public OpenGLClampedTexture(OpenGLTexture texture)
    {
        this.texture = texture;
    }

    @Override
    public void bind(GL gl, Action action)
    {
        gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_WRAP_T, GL.GL_CLAMP_TO_EDGE);
        gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_WRAP_S, GL.GL_CLAMP_TO_EDGE);
        texture.bind(gl, action);
    }
}