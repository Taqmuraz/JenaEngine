package jena.engine.graphics;

public final class GraphicsDrawingPainter implements GraphicsPainter
{
    GraphicsDrawing drawing;

    public GraphicsDrawingPainter(GraphicsDrawing drawing)
    {
        this.drawing = drawing;
    }

    @Override
    public void paint(GraphicsState graphics)
    {
        drawing.draw();
    }
}