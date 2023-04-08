package jena.opengl.texture;

import jena.engine.common.Action;
import jena.engine.graphics.TextureHandle;
import jena.opengl.OpenGLTexture;

public final class OpenGLWrapTexture implements OpenGLTexture
{
    private TextureHandle texture;

    public OpenGLWrapTexture(TextureHandle texture)
    {
        this.texture = texture;
    }

    @Override
    public void bind(Action action)
    {
        texture.bind(action);
    }
}