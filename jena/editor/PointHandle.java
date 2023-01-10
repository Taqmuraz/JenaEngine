package jena.editor;

import jena.engine.graphics.GraphicsClipPainter;
import jena.engine.math.Vector2f;

public interface PointHandle extends GraphicsClipPainter
{
    Vector2f position();
}