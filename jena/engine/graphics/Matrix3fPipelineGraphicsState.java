package jena.engine.graphics;

import jena.engine.math.Matrix3f;
import jena.engine.math.Matrix3fMul;
import jena.engine.math.Matrix3fPipeline;

public class Matrix3fPipelineGraphicsState implements GraphicsState
{
    Matrix3f matrix;
    Matrix3fPipeline pipeline;

    public Matrix3fPipelineGraphicsState(Matrix3fPipeline pipeline, Matrix3f matrix)
    {
        this.pipeline = pipeline;
        this.matrix = matrix;
    }

    @Override
    public GraphicsState transform(Matrix3f matrix)
    {
        return new Matrix3fPipelineGraphicsState(pipeline, new Matrix3fMul(this.matrix, matrix));
    }

    @Override
    public void draw(GraphicsDrawing drawing)
    {
        pipeline.matrixScope(matrix, drawing::draw);
    }
}