package jena.engine.common;

public interface ErrorHandler
{
    void call(Throwable throwable);
}