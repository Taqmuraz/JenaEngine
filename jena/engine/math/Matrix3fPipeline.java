package jena.engine.math;

import jena.engine.common.Action;
import jena.engine.graphics.Transformation;

public interface Matrix3fPipeline extends Matrix3f
{
    void matrixScope(Transformation transformation, Action action);
}