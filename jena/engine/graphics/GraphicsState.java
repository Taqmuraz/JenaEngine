package jena.engine.graphics;

public interface GraphicsState
{
    void matrixScope(Transformation transformation, GraphicsPainter painter);
}