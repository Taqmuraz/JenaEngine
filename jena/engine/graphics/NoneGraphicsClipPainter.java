package jena.engine.graphics;

public final class NoneGraphicsClipPainter implements GraphicsBrushPainter
{
    @Override
    public GraphicsPainter paint(GraphicsBrush clip) 
    {
        return new NoneGraphicsPainter();
    }
}