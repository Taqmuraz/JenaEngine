package jena.editor;

import jena.engine.graphics.Color;
import jena.engine.math.ValueFloat;
import jena.engine.math.Vector2f;

public interface GraphicsInspector
{
    PointHandle pointHandle(Vector2f position, Color color, ValueFloat radius);
}