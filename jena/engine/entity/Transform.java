package jena.engine.math;

import jena.engine.common.*;

public interface Transform
{
    void accept(ActionSingle<Matrix3f> transformable);
}