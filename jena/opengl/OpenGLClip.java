package jena.opengl;

import jena.engine.common.Action;
import jena.engine.graphics.Color;
import jena.engine.graphics.GraphicsClip;
import jena.engine.graphics.Text;
import jena.engine.graphics.TextureHandle;
import jena.engine.graphics.Transformation;
import jena.engine.math.Matrix3fMul;
import jena.engine.math.Matrix3fPipeline;
import jena.engine.math.Matrix3fRect;
import jena.engine.math.Rectf;
import jena.engine.math.ValueFloat;
import jena.engine.math.Vector2f;
import jena.engine.math.Vector2fStruct;
import jena.opengl.primitive.OpenGLPrimitiveBuilder;
import jena.opengl.texture.OpenGLTransparentTexture;

public class OpenGLClip implements GraphicsClip
{
    OpenGLTextureFunctions gl;
    OpenGLPrimitiveBuilder primitives;
    Matrix3fPipeline pipeline;

    public OpenGLClip(OpenGLTextureFunctions gl, OpenGLPrimitiveBuilder primitives, Matrix3fPipeline pipeline)
    {
        this.gl = gl;
        this.primitives = primitives;
        this.pipeline = pipeline;
    }

    @Override
    public void drawSprite(TextureHandle texture, Rectf source, Rectf destination)
    {
        if (texture instanceof OpenGLTexture)
        {
            OpenGLTexture openGLtex = (OpenGLTexture)texture;
            primitives.quad((quad, uniforms) ->
            {
                return quad.rect(source, uniforms).transformed(new Matrix3fMul(pipeline, new Matrix3fRect(destination)), uniforms);
            })
            .textured(openGLtex.point().clamp().transparent(), gl).draw();
        }
    }

    @Override
    public void drawTile(TextureHandle texture, Vector2f tiles, Rectf destination)
    {
        if (texture instanceof OpenGLTexture)
        {
            OpenGLTexture openGLtex = (OpenGLTexture)texture;
            primitives.quad((quad, uniforms) ->
            {
                Vector2fStruct t = new Vector2fStruct(tiles);
                return quad.rect(a -> a.call(0f, 0f, t.x, t.y), uniforms).transformed(new Matrix3fMul(pipeline, new Matrix3fRect(destination)), uniforms);
            })
            .textured(openGLtex.point().repeat().opaque(), gl).draw();
        }
    }

    @Override
    public void drawLine(Vector2f a, Vector2f b, Color color, ValueFloat width)
    {
        
    }

    @Override
    public void drawEllipse(Rectf rect, Color color, ValueFloat width) 
    {
        primitives.ellipseContour((ellipse, uniforms) ->
        {
            return ellipse.color(color, uniforms).transformed(new Matrix3fMul(pipeline, new Matrix3fRect(rect)), uniforms);
        }).textured(new OpenGLTransparentTexture((g, a) -> a.call()), gl).draw();
    }

    @Override
    public void drawRect(Rectf rect, Color color, ValueFloat width)
    {
        primitives.rectContour((contour, uniforms) ->
        {
            return contour.color(color, uniforms).transformed(new Matrix3fMul(pipeline, new Matrix3fRect(rect)), uniforms);
        }).textured(new OpenGLTransparentTexture((g, a) -> a.call()), gl).draw();
    }

    @Override
    public void drawText(Text text, Rectf rect, Color color)
    {

    }

    @Override
    public void fillEllipse(Rectf rect, Color color)
    {
        primitives.ellipse((ellipse, uniforms) ->
        {
            return ellipse.color(color, uniforms).transformed(new Matrix3fMul(pipeline, new Matrix3fRect(rect)), uniforms);
        }).textured(new OpenGLTransparentTexture((g, a) -> a.call()), gl).draw();
    }

    @Override
    public void fillRect(Rectf rect, Color color)
    {
        primitives.rect((shape, uniforms) ->
        {
            return shape.color(color, uniforms).transformed(new Matrix3fMul(pipeline, new Matrix3fRect(rect)), uniforms);
        }).draw();
    }

    @Override
    public void matrixScope(Transformation transformation, Action action)
    {
        pipeline.matrixScope(transformation, action);
    }
}