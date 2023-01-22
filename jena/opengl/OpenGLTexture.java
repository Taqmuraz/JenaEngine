package jena.opengl;

import com.jogamp.opengl.GL;

import jena.engine.common.Action;
import jena.engine.graphics.TextureHandle;
import jena.opengl.texture.OpenGLClampedTexture;
import jena.opengl.texture.OpenGLOpaqueTexture;
import jena.opengl.texture.OpenGLPointFilterTexture;
import jena.opengl.texture.OpenGLRepeatedTexture;
import jena.opengl.texture.OpenGLTransparentTexture;

public interface OpenGLTexture extends TextureHandle
{
    void bind(GL gl, Action action);

    default OpenGLTexture clamp()
    {
        return new OpenGLClampedTexture(this);
    }
    default OpenGLTexture repeat()
    {
        return new OpenGLRepeatedTexture(this);
    }
    default OpenGLTexture transparent()
    {
        return new OpenGLTransparentTexture(this);
    }
    default OpenGLTexture opaque()
    {
        return new OpenGLOpaqueTexture(this);
    }
    default OpenGLTexture point()
    {
        return new OpenGLPointFilterTexture(this);
    }
}