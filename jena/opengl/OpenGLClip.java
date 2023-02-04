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

public class OpenGLClip implements GraphicsClip
{
    OpenGLFunctions gl;
    OpenGLPrimitiveBuilder primitives;
    Matrix3fPipeline pipeline;

    public OpenGLClip(OpenGLFunctions gl, OpenGLPrimitiveBuilder primitives, Matrix3fPipeline pipeline)
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
            primitives.fromUniforms(uniforms ->
            {
                return primitives.quad().rect(source, uniforms).transformed(new Matrix3fMul(pipeline, new Matrix3fRect(destination)), uniforms);
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
            primitives.fromUniforms(uniforms ->
            {
                Vector2fStruct t = new Vector2fStruct(tiles);
                return primitives.quad().rect(a -> a.call(0f, 0f, t.x, t.y), uniforms).transformed(new Matrix3fMul(pipeline, new Matrix3fRect(destination)), uniforms);
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
        
    }

    @Override
    public void matrixScope(Transformation transformation, Action action)
    {
        pipeline.matrixScope(transformation, action);
    }
}