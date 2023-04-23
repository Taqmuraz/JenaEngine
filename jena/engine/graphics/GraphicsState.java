package jena.engine.graphics;

import jena.engine.math.Matrix3f;

public interface GraphicsState
{
    GraphicsState transform(Matrix3f matrix);
    void draw(GraphicsDrawing drawing);
}