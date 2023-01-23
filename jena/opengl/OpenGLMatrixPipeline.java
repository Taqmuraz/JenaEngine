package jena.opengl;

import jena.engine.common.Action;
import jena.engine.math.Matrix3fPipeline;
import jena.engine.math.Rectf;

public interface OpenGLMatrixPipeline extends Matrix3fPipeline
{
    void rectScope(Rectf rect, Action action);
}