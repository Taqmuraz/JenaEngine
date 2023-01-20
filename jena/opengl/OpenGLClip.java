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
        fillRect(destination, a -> a.call(255, 0, 255, 255));
    }

    @Override
    public void drawTile(TextureHandle texture, Vector2f tiles, Rectf destination)
    {
        fillRect(destination, a -> a.call(0, 255, 255, 255));
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