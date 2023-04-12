package jena.engine.graphics;

import jena.engine.math.Rectf;

public interface GraphicsDevice
{
    GraphicsDrawing paintRect(Rectf rect, GraphicsBrushPainter painter);
}