package jena.engine.common;

public interface ActionThrows<TError extends Throwable>
{
    void call() throws TError;
} 