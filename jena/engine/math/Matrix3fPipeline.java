package jena.engine.math;

import jena.engine.common.Action;

public interface Matrix3fPipeline
{
    void matrixScope(Matrix3f matrix, Action action);
    Matrix3f peek();
}