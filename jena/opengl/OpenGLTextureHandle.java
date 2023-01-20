package jena.opengl;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.awt.AWTTextureIO;

import jena.engine.common.Action;
import jena.engine.common.ActionSingle;
import jena.engine.common.ErrorHandler;
import jena.engine.graphics.TextureHandle;
import jena.engine.io.FileResource;

import javax.imageio.ImageIO;

public class OpenGLTextureHandle implements TextureHandle
{
    private ActionSingle<GL> binder;

    public OpenGLTextureHandle(GLProfile profile, FileResource file, ErrorHandler errorHandler)
    {
        binder = g -> { };
        file.read(stream ->
        {
            try
            {
                Texture texture = AWTTextureIO.newTexture(profile, ImageIO.read(stream), false);
                binder = gl ->
                {
                    gl.glBindTexture(GL.GL_TEXTURE_2D, texture.getTextureObject());
                };
            }
            catch(Throwable error)
            {
                errorHandler.call(error);
            }
        }, errorHandler);
    }

    public void bind(GL gl, Action action)
    {
        gl.glEnable(GL.GL_TEXTURE_2D);
        binder.call(gl);
        action.call();
        gl.glBindTexture(GL.GL_TEXTURE_2D, 0);
        gl.glDisable(GL.GL_TEXTURE_2D);
        gl.glDisable(GL.GL_BLEND);
    }
    public void bindTransparent(GL gl, Action action)
    {
        gl.glEnable(GL.GL_BLEND);
        gl.glBlendFunc(GL.GL_ONE, GL.GL_ONE_MINUS_SRC_ALPHA);
        bind(gl, action);
        gl.glDisable(GL.GL_BLEND);
    }
}