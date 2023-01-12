package jena.engine.input;

import jena.engine.math.Vector2f;

public interface Mouse
{
    Vector2f position();
    Key button(int button);
}