package jena.engine.game;

import jena.engine.entity.DeltaTime;
import jena.engine.entity.Time;
import jena.engine.graphics.GraphicsBrush;
import jena.engine.graphics.GraphicsBrushPainter;
import jena.engine.graphics.GraphicsDrawingPainter;
import jena.engine.graphics.GraphicsPainter;
import jena.engine.math.ValueFloat;

public final class FPSBrushPainter implements GraphicsBrushPainter
{
    ValueFloat deltaTime;
    public FPSBrushPainter()
    {
        deltaTime = new DeltaTime(new Time());
    }

    @Override
    public GraphicsPainter paint(GraphicsBrush clip)
    {
        return new GraphicsDrawingPainter(
            clip.drawText(a -> deltaTime.accept(time ->  a.call(String.format("fps = %s", String.valueOf((int)(1f / time))))), a -> a.call(0f, 0f, 1, 1), a -> a.call(255, 255, 255, 255)));
    }
}