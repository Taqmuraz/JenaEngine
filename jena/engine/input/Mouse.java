package jena.engine.input;

import jena.engine.math.Vector2f;
import jena.engine.math.ValueInt;

public interface Mouse
{
    Vector2f position();
    ValueInt buttonState(int button);
}