package jena.engine.common;

public interface Acceptor<T>
{
    void call(T arg);
}