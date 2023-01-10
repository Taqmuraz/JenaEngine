package jena.engine.graphics;

import jena.engine.math.Matrix3f;

public interface Transformation
{
    Matrix3f transform(Matrix3f source);
}