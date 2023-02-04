package jena.opengl.texture;

import jena.engine.common.Action;
import jena.opengl.OpenGLTexture;
import jena.opengl.OpenGLTextureFunctions;

public class OpenGLOpaqueTexture implements OpenGLTexture
{
    OpenGLTexture texture;

    public OpenGLOpaqueTexture(OpenGLTexture texture)
    {
        this.texture = texture;
    }

    @Override
    public void bind(OpenGLTextureFunctions gl, Action action)
    {
        gl.disableBlend();
        texture.bind(gl, action);
    }
}