package jena.jogl;

import javax.imageio.ImageIO;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.awt.AWTTextureIO;

import jena.engine.common.Action;
import jena.engine.common.ErrorHandler;
import jena.engine.io.InputStreamFromFlow;
import jena.engine.io.StorageResource;
import jena.opengl.OpenGLTexture;
import jena.opengl.OpenGLTextureFunctions;

public class JOGLTextureFunctions implements OpenGLTextureFunctions
{
    static class JOGLTexture implements OpenGLTexture
    {
        OpenGLTexture binder;

        JOGLTexture(GLProfile profile, StorageResource file, ErrorHandler errorHandler)
        {
            binder = (gl, action) -> {};
            file.read(stream ->
            {
                try
                {
                    Texture texture = AWTTextureIO.newTexture(profile, ImageIO.read(new InputStreamFromFlow(stream)), false);
                    binder = (gl, action) ->
                    {
                        if (gl instanceof JOGLTextureFunctions)
                        {
                            ((JOGLTextureFunctions)gl).bind(texture, action);
                        }
                    };
                } catch (Throwable error) 
                {
                    errorHandler.call(error);
                }
            }, errorHandler);
        }

        @Override
        public void bind(OpenGLTextureFunctions gl, Action action)
        {
            binder.bind(gl, action);
        }
    }

    GL gl;

    public JOGLTextureFunctions(GL gl)
    {
        this.gl = gl;
    }

    private void bind(Texture texture, Action action)
    {
        gl.glEnable(GL.GL_TEXTURE_2D);
        gl.glBindTexture(GL.GL_TEXTURE_2D, texture.getTextureObject());
        action.call();
        gl.glBindTexture(GL.GL_TEXTURE_2D, 0);
        gl.glDisable(GL.GL_TEXTURE_2D);
    }

    @Override
    public void repeat()
    {
        gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_WRAP_T, GL.GL_REPEAT);
        gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_WRAP_S, GL.GL_REPEAT);
    }

    @Override
    public void clamp()
    {
        gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_WRAP_T, GL.GL_CLAMP_TO_EDGE);
        gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_WRAP_S, GL.GL_CLAMP_TO_EDGE);
    }

    @Override
    public void enableBlend()
    {
        gl.glEnable(GL.GL_BLEND);
        gl.glBlendFunc(GL.GL_ONE, GL.GL_ONE_MINUS_SRC_ALPHA);
    }

    @Override
    public void disableBlend()
    {
        gl.glDisable(GL.GL_BLEND);
    }

    @Override
    public void enableFilter()
    {
        gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MIN_FILTER, GL.GL_LINEAR);
        gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MAG_FILTER, GL.GL_LINEAR);
    }

    @Override
    public void disableFilter()
    {
        gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MIN_FILTER, GL.GL_NEAREST);
        gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MAG_FILTER, GL.GL_NEAREST);
    }
}