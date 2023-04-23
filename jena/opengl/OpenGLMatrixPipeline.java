package jena.opengl;

import jena.engine.math.Matrix3fPipeline;
import jena.engine.math.Rectf;

public interface OpenGLMatrixPipeline extends Matrix3fPipeline
{
    OpenGLRectScope rectScope(Rectf rect);
}