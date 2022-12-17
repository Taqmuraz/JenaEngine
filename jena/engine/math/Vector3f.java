package jena.engine.math;

public interface Vector3f
{
    void accept(Vector3fAcceptor acceptor);
    float length();
    Vector3f clone();
}