package jena.engine.graphics;

import jena.engine.common.ArrayIterable;
import jena.engine.common.CachedIterable;
import jena.engine.common.MapIterable;

public final class CompositeGraphicsBrushPainter implements GraphicsBrushPainter
{
    Iterable<GraphicsBrushPainter> painters;

    public CompositeGraphicsBrushPainter(Iterable<GraphicsBrushPainter> painters)
    {
        this.painters = painters;
    }
    public CompositeGraphicsBrushPainter(GraphicsBrushPainter... painters)
    {
        this.painters = new ArrayIterable<>(painters);
    }
    @Override
    public GraphicsPainter paint(GraphicsBrush clip)
    {
        return new CompositeGraphicsPainter(
            new CachedIterable<>(
                new MapIterable<>(painters, p -> p.paint(clip))));
    }
}