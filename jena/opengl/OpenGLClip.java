package jena.opengl;

import com.jogamp.opengl.GL2;

import jena.engine.common.Action;
import jena.engine.graphics.Color;
import jena.engine.graphics.ColorFloatStruct;
import jena.engine.graphics.GraphicsClip;
import jena.engine.graphics.Text;
import jena.engine.graphics.TextureHandle;
import jena.engine.graphics.Transformation;
import jena.engine.math.Matrix3fPipeline;
import jena.engine.math.Rectf;
import jena.engine.math.ValueFloat;
import jena.engine.math.Vector2f;

public class OpenGLClip implements GraphicsClip
{
    GL2 gl;
    Matrix3fPipeline matrixStack;

    public OpenGLClip(GL2 gl, Matrix3fPipeline matrixPipeline)
    {
        this.gl = gl;
        matrixStack = matrixPipeline;
    }

    @Override
    public void drawSprite(TextureHandle texture, Rectf source, Rectf destination)
    {
        if (texture instanceof OpenGLTextureHandle)
        {
            OpenGLTextureHandle openGLtex = (OpenGLTextureHandle)texture;
            source.accept((sx, sy, sw, sh) -> destination.accept((dx, dy, dw, dh) -> openGLtex.bindTransparent(gl, () ->
            {
                gl.glTexEnvi(GL2.GL_TEXTURE_ENV, GL2.GL_TEXTURE_ENV_MODE, GL2.GL_MODULATE);
                gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_WRAP_T, GL2.GL_CLAMP);
                gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_WRAP_S, GL2.GL_CLAMP);
                gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_MIN_FILTER, GL2.GL_NEAREST);
                gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_MAG_FILTER, GL2.GL_NEAREST);
                gl.glColor3f(1f, 1f, 1f);
                gl.glBegin(GL2.GL_QUADS);
                gl.glTexCoord2f(sx, sy + sh);
                gl.glVertex2f(dx, dy);
                gl.glTexCoord2f(sx, sy);
                gl.glVertex2f(dx, dy + dh);
                gl.glTexCoord2f(sx + sw, sy);
                gl.glVertex2f(dx + dw, dy + dh);
                gl.glTexCoord2f(sx + sw, sy + sh);
                gl.glVertex2f(dx + dw, dy);
                gl.glEnd();
            })));
        }
    }

    @Override
    public void drawTile(TextureHandle texture, Vector2f tiles, Rectf destination)
    {
        if (texture instanceof OpenGLTextureHandle)
        {
            OpenGLTextureHandle openGLtex = (OpenGLTextureHandle)texture;
            tiles.accept((sw, sh) -> destination.accept((dx, dy, dw, dh) -> openGLtex.bind(gl, () ->
            {
                gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_WRAP_T, GL2.GL_REPEAT);
                gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_WRAP_S, GL2.GL_REPEAT);
                gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_MIN_FILTER, GL2.GL_NEAREST);
                gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_MAG_FILTER, GL2.GL_NEAREST);
                gl.glColor3f(1f, 1f, 1f);
                gl.glBegin(GL2.GL_QUADS);
                gl.glTexCoord2f(0f, sh);
                gl.glVertex2f(dx, dy);
                gl.glTexCoord2f(0f, 0f);
                gl.glVertex2f(dx, dy + dh);
                gl.glTexCoord2f(sw, 0f);
                gl.glVertex2f(dx + dw, dy + dh);
                gl.glTexCoord2f(sw, sh);
                gl.glVertex2f(dx + dw, dy);
                gl.glEnd();
            })));
        }
    }

    @Override
    public void drawLine(Vector2f a, Vector2f b, Color color, ValueFloat width)
    {
        
    }

    @Override
    public void drawEllipse(Rectf rect, Color color, ValueFloat width) 
    {

    }

    @Override
    public void drawRect(Rectf rect, Color color, ValueFloat width)
    {

    }

    @Override
    public void drawText(Text text, Rectf rect, Color color)
    {

    }

    @Override
    public void fillEllipse(Rectf rect, Color color)
    {

    }

    int uniqueColor;

    @Override
    public void fillRect(Rectf rect, Color color)
    {
        rect.accept((x, y, w, h) ->
        {
            gl.glBegin(GL2.GL_QUADS);
            new ColorFloatStruct(color).acceptFloats(gl::glColor4f);
            gl.glVertex2f(x, y);
            gl.glVertex2f(x, y + h);
            gl.glVertex2f(x + w, y + h);
            gl.glVertex2f(x + w, y);
            gl.glEnd();
        });
    }

    @Override
    public void matrixScope(Transformation transformation, Action action)
    {
        matrixStack.matrixScope(transformation, action);
    }
}