package jena.engine.input;

import jena.engine.math.Vector2f;

public interface Keyboard
{
    Vector2f movement();
    Key keyOf(char symbol);
}