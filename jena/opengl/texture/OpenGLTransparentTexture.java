package jena.opengl.texture;

import com.jogamp.opengl.GL;

import jena.engine.common.Action;
import jena.opengl.OpenGLTexture;

public class OpenGLTransparentTexture implements OpenGLTexture
{
    OpenGLTexture texture;

    public OpenGLTransparentTexture(OpenGLTexture texture)
    {
        this.texture = texture;
    }

    @Override
    public void bind(GL gl, Action action)
    {
        gl.glEnable(GL.GL_BLEND);
        gl.glBlendFunc(GL.GL_ONE, GL.GL_ONE_MINUS_SRC_ALPHA);
        texture.bind(gl, action);
    }
}