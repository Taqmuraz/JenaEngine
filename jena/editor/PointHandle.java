package jena.editor;

import jena.engine.graphics.GraphicsPainter;
import jena.engine.math.Vector2f;

public interface PointHandle extends GraphicsPainter
{
    Vector2f position();
}