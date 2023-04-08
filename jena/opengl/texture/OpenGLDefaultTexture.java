package jena.opengl.texture;

import jena.engine.common.Action;
import jena.opengl.OpenGLTexture;

public final class OpenGLDefaultTexture implements OpenGLTexture
{
    @Override
    public void bind(Action action)
    {
        action.call();
    }
}