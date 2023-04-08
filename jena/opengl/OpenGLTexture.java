package jena.opengl;

import jena.engine.graphics.TextureHandle;
import jena.opengl.texture.OpenGLClampedTexture;
import jena.opengl.texture.OpenGLOpaqueTexture;
import jena.opengl.texture.OpenGLPointFilterTexture;
import jena.opengl.texture.OpenGLRepeatedTexture;
import jena.opengl.texture.OpenGLTransparentTexture;

public interface OpenGLTexture extends TextureHandle
{
    default OpenGLTexture clamp(OpenGLTextureFunctions gl)
    {
        return new OpenGLClampedTexture(gl, this);
    }
    default OpenGLTexture repeat(OpenGLTextureFunctions gl)
    {
        return new OpenGLRepeatedTexture(gl, this);
    }
    default OpenGLTexture transparent(OpenGLTextureFunctions gl)
    {
        return new OpenGLTransparentTexture(gl, this);
    }
    default OpenGLTexture opaque(OpenGLTextureFunctions gl)
    {
        return new OpenGLOpaqueTexture(gl, this);
    }
    default OpenGLTexture point(OpenGLTextureFunctions gl)
    {
        return new OpenGLPointFilterTexture(gl, this);
    }
}