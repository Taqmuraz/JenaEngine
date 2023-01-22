package jena.opengl;

import com.jogamp.opengl.GL;

import jena.engine.common.Action;
import jena.engine.common.ActionSingle;
import jena.engine.graphics.Color;
import jena.engine.graphics.GraphicsClip;
import jena.engine.graphics.Text;
import jena.engine.graphics.TextureHandle;
import jena.engine.graphics.Transformation;
import jena.engine.math.Matrix3fPipeline;
import jena.engine.math.Rectf;
import jena.engine.math.RectfStruct;
import jena.engine.math.ValueFloat;
import jena.engine.math.Vector2f;
import jena.opengl.primitive.OpenGLPrimitiveBuilder;

public class OpenGLClip implements GraphicsClip
{
    GL gl;
    ActionSingle<Rectf> quad;
    Matrix3fPipeline matrixStack;

    public OpenGLClip(GL gl, OpenGLPrimitiveBuilder primitives, Matrix3fPipeline matrixPipeline)
    {
        this.gl = gl;

        quad = new ActionSingle<Rectf>()
        {
            RectfStruct rect = new RectfStruct();
            OpenGLPrimitive quad = primitives.quad(rect);

            @Override
            public void call(Rectf rect)
            {
                this.rect.apply(rect);
                quad.draw();
            }
        };
        matrixStack = matrixPipeline;
    }

    @Override
    public void drawSprite(TextureHandle texture, Rectf source, Rectf destination)
    {
        if (texture instanceof OpenGLDiffuseTexture)
        {
            OpenGLDiffuseTexture openGLtex = (OpenGLDiffuseTexture)texture;
            openGLtex.point().clamp().transparent().bind(gl, () ->
            {
                quad.call(destination);
            });
        }
    }

    @Override
    public void drawTile(TextureHandle texture, Vector2f tiles, Rectf destination)
    {
        if (texture instanceof OpenGLDiffuseTexture)
        {
            OpenGLDiffuseTexture openGLtex = (OpenGLDiffuseTexture)texture;
            openGLtex.point().repeat().opaque().bind(gl, () ->
            {
                quad.call(destination);
            });
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

    @Override
    public void fillRect(Rectf rect, Color color)
    {
        rect.accept((x, y, w, h) ->
        {
        });
    }

    @Override
    public void matrixScope(Transformation transformation, Action action)
    {
        matrixStack.matrixScope(transformation, action);
    }
}