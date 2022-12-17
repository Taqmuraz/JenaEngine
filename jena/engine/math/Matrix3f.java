package jena.engine.math;

public interface Matrix3f
{
    void accept(Matrix3fAcceptor acceptor);
    Matrix3f clone();
}