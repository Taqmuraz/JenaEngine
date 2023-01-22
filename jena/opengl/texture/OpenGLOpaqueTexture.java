package jena.opengl.texture;

import com.jogamp.opengl.GL;

import jena.engine.common.Action;
import jena.opengl.OpenGLTexture;

public class OpenGLOpaqueTexture implements OpenGLTexture
{
    OpenGLTexture texture;

    public OpenGLOpaqueTexture(OpenGLTexture texture)
    {
        this.texture = texture;
    }

    @Override
    public void bind(GL gl, Action action)
    {
        gl.glDisable(GL.GL_BLEND);
        texture.bind(gl, action);
    }
}