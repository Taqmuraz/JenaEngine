package jena.engine.graphics;

public final class NoneGraphicsPainter implements GraphicsPainter
{
    @Override
    public GraphicsDrawing paint(GraphicsState graphics)
    {
        return new NoneGraphicsDrawing();
    }
}