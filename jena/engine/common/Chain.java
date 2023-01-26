package jena.engine.common;

public interface Chain<TIn, TOut>
{
    <NOut> Chain<TOut, NOut> join(FunctionSingle<TOut, NOut> out);
    TOut close(TIn in);
}