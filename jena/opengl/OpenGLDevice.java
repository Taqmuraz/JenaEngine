package jena.opengl;

import jena.engine.common.ErrorHandler;
import jena.engine.common.Function;
import jena.engine.graphics.GraphicsClipPainter;
import jena.engine.graphics.GraphicsDevice;
import jena.engine.math.Rectf;
import jena.opengl.primitive.OpenGLPrimitiveBuilder;

public class OpenGLDevice implements GraphicsDevice
{
    OpenGLTextureFunctions gl;
    Rectf paintArea;
    OpenGLPrimitiveBuilder primitives;
    Function<OpenGLMatrixPipeline> pipelineConstructor;

    public OpenGLDevice(OpenGLTextureFunctions gl, Function<OpenGLMatrixPipeline> pipelineConstructor, OpenGLPrimitiveBuilder primitives, Rectf paintArea, ErrorHandler errorHandler)
    {
        this.pipelineConstructor = pipelineConstructor;
        this.gl = gl;
        this.paintArea = paintArea;
        this.primitives = primitives;
    }

    @Override
    public void paintRect(Rectf rect, GraphicsClipPainter painter)
    {
        paintArea.accept((px, py, pw, ph) -> rect.accept((x, y, w, h) ->
        {
            OpenGLMatrixPipeline pipeline = pipelineConstructor.call();
            pipeline.rectScope(a -> a.call(x + px, y + py, w, h), () ->
            {
                painter.paint(new OpenGLClip(gl, primitives, pipeline));
            });
        }));
    }
}