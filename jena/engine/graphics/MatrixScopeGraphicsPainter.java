package jena.engine.graphics;

import jena.engine.math.Matrix3f;

public final class MatrixScopeGraphicsPainter implements GraphicsPainter
{
    Matrix3f matrix;
    GraphicsPainter painter;

    public MatrixScopeGraphicsPainter(Matrix3f matrix, GraphicsPainter painter)
    {
        this.matrix = matrix;
        this.painter = painter;
    }

    @Override
    public GraphicsDrawing paint(GraphicsState state)
    {
        return painter.paint(state.transform(matrix));
    }
}