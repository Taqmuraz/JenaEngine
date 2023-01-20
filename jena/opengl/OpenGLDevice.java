package jena.opengl;

import com.jogamp.opengl.GL2;

import jena.engine.graphics.GraphicsClipPainter;
import jena.engine.graphics.GraphicsDevice;
import jena.engine.math.Rectf;

public class OpenGLDevice implements GraphicsDevice
{
    GL2 gl;
    Rectf paintArea;

    public OpenGLDevice(GL2 gl, Rectf paintArea)
    {
        this.gl = gl;
        this.paintArea = paintArea;
    }

    @Override
    public void paintRect(Rectf rect, GraphicsClipPainter painter)
    {
        paintArea.accept((px, py, pw, ph) -> rect.accept((x, y, w, h) ->
        {
            OpenGLMatrixPipeline pipeline = new OpenGLMatrixPipeline(gl);
            pipeline.rectScope(a -> a.call(x + px, y + py, w, h), () ->
            {
                painter.paint(new OpenGLClip(gl, pipeline));
            });
        }));
    }
}