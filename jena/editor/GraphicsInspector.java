package jena.editor;

import jena.engine.common.Function;
import jena.engine.graphics.Color;
import jena.engine.graphics.GraphicsClipPainter;
import jena.engine.graphics.Transformation;
import jena.engine.math.ValueFloat;
import jena.engine.math.Vector2f;

public interface GraphicsInspector
{
    PointHandle pointHandle(Vector2f position, Color color, ValueFloat radius);
    GraphicsClipPainter matrixScope(Transformation transformation, Function<GraphicsClipPainter> function);
}