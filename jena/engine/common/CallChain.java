package jena.engine.common;

public class CallChain<TIn, TOut> implements Chain<TIn, TOut, TOut>
{
    FunctionSingle<TIn, TOut> value;

    public CallChain(FunctionSingle<TIn, TOut> value)
    {
        this.value = value;
    }

    @Override
    public <NIn> Chain<NIn, TIn, TOut> join(FunctionSingle<NIn, TIn> out)
    {
        return new CallJoinChain<NIn, TIn, TOut>(v -> value.call(out.call(v)));
    }

    @Override
    public TOut close(TIn in)
    {
        return value.call(in);
    }
}