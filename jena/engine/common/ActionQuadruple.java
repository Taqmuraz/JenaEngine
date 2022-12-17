package jena.engine.common;

public interface ActionQuadruple<TArg0, TArg1, TArg2, TArg3>
{
    void call(TArg0 arg0, TArg1 arg1, TArg2 arg2, TArg3 arg3);
}