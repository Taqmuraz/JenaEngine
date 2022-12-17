package jena.engine.math;

public interface Vector2f
{
    void accept(Vector2fAcceptor acceptor);
    float length();
    Vector2f clone();
}