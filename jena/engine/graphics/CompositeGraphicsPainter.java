package jena.engine.graphics;

import jena.engine.common.ArrayIterable;
import jena.engine.common.CachedIterable;
import jena.engine.common.MapIterable;

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
    public GraphicsDrawing paint(GraphicsState state)
    {
        return new CompositeGraphicsDrawing(
            new CachedIterable<>(
                new MapIterable<>(
                    painters,
                    painter -> painter.paint(state))));
    }
}