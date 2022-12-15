package jena.engine.common;

public interface ActionSingle<TArg>
{
    void call(TArg arg);
}