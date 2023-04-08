package jena.opengl.texture;

import jena.engine.common.Action;
import jena.opengl.OpenGLTexture;
import jena.opengl.OpenGLTextureFunctions;

public class OpenGLTransparentTexture implements OpenGLTexture
{
    OpenGLTexture texture;
    OpenGLTextureFunctions gl;

    public OpenGLTransparentTexture(OpenGLTextureFunctions gl, OpenGLTexture texture)
    {
        this.gl = gl;
        this.texture = texture;
    }

    @Override
    public void bind(Action action)
    {
        gl.enableBlend();
        texture.bind(action);
        gl.disableBlend();
    }
}