package jena.opengl;

import jena.engine.common.ErrorHandler;
import jena.engine.common.Function;
import jena.engine.graphics.GraphicsBrushPainter;
import jena.engine.graphics.GraphicsDevice;
import jena.engine.graphics.GraphicsDrawing;
import jena.engine.graphics.GraphicsPainter;
import jena.engine.graphics.MatrixScopeGraphicsPainter;
import jena.engine.math.Matrix3f;
import jena.engine.math.Matrix3fPipeline;
import jena.engine.math.Matrix3fRect;
import jena.engine.math.Rectf;
import jena.opengl.primitive.OpenGLPrimitiveBuilder;

public class OpenGLDevice implements GraphicsDevice
{
    OpenGLTextureFunctions gl;
    Rectf paintArea;
    OpenGLPrimitiveBuilder primitives;
    Function<Matrix3fPipeline> pipelineConstructor;

    public OpenGLDevice(OpenGLTextureFunctions gl, Function<Matrix3fPipeline> pipelineConstructor, OpenGLPrimitiveBuilder primitives, Rectf paintArea, ErrorHandler errorHandler)
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
        Matrix3fPipeline pipeline = pipelineConstructor.call();
        OpenGLClip clip = new OpenGLClip(gl, primitives, pipeline);

        Matrix3f matrix = new Matrix3fRect(a -> area.accept((x, y, w, h) -> a.call(-1f, 1f, 2f / w, -2f / h)));
        GraphicsPainter gp = new MatrixScopeGraphicsPainter(matrix, painter.paint(clip));
        GraphicsDrawing drawing = gp.paint(clip);
        return drawing;
    }
}