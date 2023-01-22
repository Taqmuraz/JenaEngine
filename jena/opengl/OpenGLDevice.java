package jena.opengl;

import com.jogamp.opengl.GL2;

import jena.engine.common.ErrorHandler;
import jena.engine.graphics.GraphicsClipPainter;
import jena.engine.graphics.GraphicsDevice;
import jena.engine.math.Rectf;
import jena.opengl.gles.OpenGLESBufferPrimitiveBuilder;

public class OpenGLDevice implements GraphicsDevice
{
    GL2 gl;
    Rectf paintArea;
    OpenGLESBufferPrimitiveBuilder primitives;

    public OpenGLDevice(GL2 gl, Rectf paintArea, ErrorHandler errorHandler)
    {
        this.gl = gl;
        this.paintArea = paintArea;
        primitives = new OpenGLESBufferPrimitiveBuilder(gl, errorHandler);
    }

    @Override
    public void paintRect(Rectf rect, GraphicsClipPainter painter)
    {
        paintArea.accept((px, py, pw, ph) -> rect.accept((x, y, w, h) ->
        {
            OpenGLMatrixPipeline pipeline = new OpenGLMatrixPipeline(gl);
            pipeline.rectScope(a -> a.call(x + px, y + py, w, h), () ->
            {
                painter.paint(new OpenGLClip(gl, primitives, pipeline));
            });
        }));
    }
}