package jena.engine.graphics;

import jena.engine.common.ArrayIterable;

public final class CompositeGraphicsPainter implements GraphicsPainter
{
    Iterable<GraphicsPainter> painters;

    public CompositeGraphicsPainter(Iterable<GraphicsPainter> painters)
    {
        this.painters = painters;
    }
    public CompositeGraphicsPainter(GraphicsPainter... painters)
    {
        this.painters = new ArrayIterable<>(painters);
    }
    @Override
    public void paint(GraphicsState state)
    {
        for(GraphicsPainter painter : painters) painter.paint(state);
    }
}