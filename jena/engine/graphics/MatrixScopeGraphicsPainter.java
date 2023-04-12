package jena.engine.graphics;

public final class MatrixScopeGraphicsPainter implements GraphicsPainter
{
    Transformation transformation;
    GraphicsPainter painter;

    public MatrixScopeGraphicsPainter(Transformation transformation, GraphicsPainter painter)
    {
        this.transformation = transformation;
        this.painter = painter;
    }

    @Override
    public void paint(GraphicsState graphics)
    {
        graphics.matrixScope(transformation, painter);
    }
}