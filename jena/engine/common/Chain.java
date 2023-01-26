package jena.engine.common;

public interface Chain<TIn, TOut, TResult>
{
    <NIn> Chain<NIn, TIn, TResult> join(FunctionSingle<NIn, TIn> out);
    TResult close(TIn in);
}