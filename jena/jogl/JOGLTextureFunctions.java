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
    class JOGLTexture implements OpenGLTexture
    {
        OpenGLTexture binder;

        JOGLTexture(GLProfile profile, StorageResource file, ErrorHandler errorHandler)
        {
            binder = action -> action.call();
            file.read(stream ->
            {
                try
                {
                    Texture texture = AWTTextureIO.newTexture(profile, ImageIO.read(new InputStreamFromFlow(stream)), false);
                    binder = action ->
                    {
                        JOGLTextureFunctions.this.bind(texture, action);
                    };
                } catch (Throwable error) 
                {
                    errorHandler.call(error);
                }
            }, errorHandler);
        }

        @Override
        public void bind(Action action)
        {
            binder.bind(action);
        }
    }

    JOGL_ES3_Provider gl;

    public JOGLTextureFunctions(JOGL_ES3_Provider gl)
    {
        this.gl = gl;
    }

    private void bind(Texture texture, Action action)
    {
        gl.gl().glEnable(GL.GL_TEXTURE_2D);
        gl.gl().glBindTexture(GL.GL_TEXTURE_2D, texture.getTextureObject());
        action.call();
        gl.gl().glBindTexture(GL.GL_TEXTURE_2D, 0);
        gl.gl().glDisable(GL.GL_TEXTURE_2D);
    }

    @Override
    public void repeat()
    {
        gl.gl().glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_WRAP_T, GL.GL_REPEAT);
        gl.gl().glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_WRAP_S, GL.GL_REPEAT);
    }

    @Override
    public void clamp()
    {
        gl.gl().glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_WRAP_T, GL.GL_CLAMP_TO_EDGE);
        gl.gl().glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_WRAP_S, GL.GL_CLAMP_TO_EDGE);
    }

    @Override
    public void enableBlend()
    {
        gl.gl().glEnable(GL.GL_BLEND);
        gl.gl().glBlendFunc(GL.GL_ONE, GL.GL_ONE_MINUS_SRC_ALPHA);
    }

    @Override
    public void disableBlend()
    {
        gl.gl().glDisable(GL.GL_BLEND);
    }

    @Override
    public void enableFilter()
    {
        gl.gl().glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MIN_FILTER, GL.GL_LINEAR);
        gl.gl().glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MAG_FILTER, GL.GL_LINEAR);
    }

    @Override
    public void disableFilter()
    {
        gl.gl().glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MIN_FILTER, GL.GL_NEAREST);
        gl.gl().glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MAG_FILTER, GL.GL_NEAREST);
    }
}