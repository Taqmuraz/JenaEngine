package jena.engine.graphics;

import jena.engine.common.ArrayIterable;

public final class CompositeGraphicsDrawing implements GraphicsDrawing
{
    Iterable<GraphicsDrawing> drawings;

    public CompositeGraphicsDrawing(Iterable<GraphicsDrawing> drawings)
    {
        this.drawings = drawings;
    }
    public CompositeGraphicsDrawing(GraphicsDrawing... drawings)
    {
        this.drawings = new ArrayIterable<>(drawings);
    }

    @Override
    public void draw()
    {
        for(GraphicsDrawing drawing : drawings) drawing.draw();
    }
}