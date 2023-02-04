package jena.opengl.texture;

import jena.engine.common.Action;
import jena.opengl.OpenGLTexture;
import jena.opengl.OpenGLTextureFunctions;

public class OpenGLTransparentTexture implements OpenGLTexture
{
    OpenGLTexture texture;

    public OpenGLTransparentTexture(OpenGLTexture texture)
    {
        this.texture = texture;
    }

    @Override
    public void bind(OpenGLTextureFunctions gl, Action action)
    {
        gl.enableBlend();
        texture.bind(gl, action);
        gl.disableBlend();
    }
}