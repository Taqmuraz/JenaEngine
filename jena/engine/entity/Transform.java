package jena.engine.entity;

import jena.engine.common.*;
import jena.engine.math.Matrix3f;

public interface Transform
{
    void acceptMatrix(ActionSingle<Matrix3f> transformable);
}