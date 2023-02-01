package jena.engine.common;

public interface Acceptable<T>
{
    void accept(Acceptor<T> acceptor);
}