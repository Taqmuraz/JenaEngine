package jena.engine.graphics;

import jena.engine.math.Matrix3f;

public final class MultiplicationTransformation implements Transformation
{
    private Matrix3f transform;

    public MultiplicationTransformation(Matrix3f transform)
    {
        this.transform = transform;
    }

    @Override
    public Matrix3f transform(Matrix3f source)
    {
        return source.multiply(transform);
    }
}