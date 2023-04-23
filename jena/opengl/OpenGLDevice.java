package jena.opengl;

import jena.engine.common.ErrorHandler;
import jena.engine.common.Function;
import jena.engine.graphics.GraphicsBrushPainter;
import jena.engine.graphics.GraphicsDevice;
import jena.engine.graphics.GraphicsDrawing;
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
    public GraphicsDrawing paintRect(Rectf rect, GraphicsBrushPainter painter)
    {
        Rectf area = a -> paintArea.accept((px, py, pw, ph) -> rect.accept((x, y, w, h) -> a.call(x + px, y + py, w, h)));
        OpenGLMatrixPipeline pipeline = pipelineConstructor.call();
        OpenGLClip clip = new OpenGLClip(gl, primitives, pipeline);
        GraphicsDrawing drawing = painter.paint(clip).paint(clip);
        OpenGLRectScope scope = pipeline.rectScope(area);
        return () -> scope.draw(drawing);
    }
}