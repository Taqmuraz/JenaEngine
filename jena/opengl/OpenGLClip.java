package jena.opengl;

import jena.engine.graphics.Color;
import jena.engine.graphics.GraphicsBrush;
import jena.engine.graphics.GraphicsDrawing;
import jena.engine.graphics.GraphicsState;
import jena.engine.graphics.Matrix3fPipelineGraphicsState;
import jena.engine.graphics.NoneGraphicsDrawing;
import jena.engine.graphics.Text;
import jena.engine.graphics.TextureHandle;
import jena.engine.math.Matrix3f;
import jena.engine.math.Matrix3fMul;
import jena.engine.math.Matrix3fPipeline;
import jena.engine.math.Matrix3fRect;
import jena.engine.math.Rectf;
import jena.engine.math.ValueFloat;
import jena.engine.math.Vector2f;
import jena.engine.math.Vector2fStruct;
import jena.opengl.primitive.OpenGLPrimitiveBuilder;
import jena.opengl.texture.OpenGLDefaultTexture;
import jena.opengl.texture.OpenGLTransparentTexture;
import jena.opengl.texture.OpenGLWrapTexture;

public class OpenGLClip implements GraphicsBrush, GraphicsState
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
    public GraphicsDrawing drawSprite(TextureHandle texture, Rectf source, Rectf destination)
    {
        return primitives.quad((quad, uniforms) ->
        {
            return quad.rect(source, uniforms).transformed(new Matrix3fMul(pipeline.peek(), new Matrix3fRect(destination)), uniforms);
        })
        .textured(new OpenGLWrapTexture(texture).point(gl).clamp(gl).transparent(gl));
    }

    @Override
    public GraphicsDrawing drawTile(TextureHandle texture, Vector2f tiles, Rectf destination)
    {
        return primitives.quad((quad, uniforms) ->
        {
            Vector2fStruct t = new Vector2fStruct(tiles);
            return quad.rect(a -> a.call(0f, 0f, t.x, t.y), uniforms).transformed(new Matrix3fMul(pipeline.peek(), new Matrix3fRect(destination)), uniforms);
        })
        .textured(new OpenGLWrapTexture(texture).point(gl).repeat(gl).opaque(gl));
    }

    @Override
    public GraphicsDrawing drawLine(Vector2f a, Vector2f b, Color color, ValueFloat width)
    {
        return new NoneGraphicsDrawing();
    }

    @Override
    public GraphicsDrawing drawEllipse(Rectf rect, Color color, ValueFloat width) 
    {
        return primitives.ellipseContour((ellipse, uniforms) ->
        {
            return ellipse.color(color, uniforms).transformed(new Matrix3fMul(pipeline.peek(), new Matrix3fRect(rect)), uniforms);
        }).textured(new OpenGLTransparentTexture(gl, new OpenGLDefaultTexture()));
    }

    @Override
    public GraphicsDrawing drawRect(Rectf rect, Color color, ValueFloat width)
    {
        return primitives.rectContour((contour, uniforms) ->
        {
            return contour.color(color, uniforms).transformed(new Matrix3fMul(pipeline.peek(), new Matrix3fRect(rect)), uniforms);
        }).textured(new OpenGLTransparentTexture(gl, new OpenGLDefaultTexture()));
    }

    @Override
    public GraphicsDrawing drawText(Text text, Rectf rect, Color color)
    {
        return new NoneGraphicsDrawing();
    }

    @Override
    public GraphicsDrawing fillEllipse(Rectf rect, Color color)
    {
        return primitives.ellipse((ellipse, uniforms) ->
        {
            return ellipse.color(color, uniforms).transformed(new Matrix3fMul(pipeline.peek(), new Matrix3fRect(rect)), uniforms);
        }).textured(new OpenGLTransparentTexture(gl, new OpenGLDefaultTexture()));
    }

    @Override
    public GraphicsDrawing fillRect(Rectf rect, Color color)
    {
        return primitives.rect((shape, uniforms) ->
        {
            return shape.color(color, uniforms).transformed(new Matrix3fMul(pipeline.peek(), new Matrix3fRect(rect)), uniforms);
        }).textured(new OpenGLTransparentTexture(gl, new OpenGLDefaultTexture()));
    }

    @Override
    public GraphicsState transform(Matrix3f matrix)
    {
        return new Matrix3fPipelineGraphicsState(pipeline, matrix);
    }

    @Override
    public void draw(GraphicsDrawing drawing)
    {
        drawing.draw();
    }
}