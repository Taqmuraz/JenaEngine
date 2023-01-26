package jena.engine.common;

class CallJoinChain<TIn, TOut, TResult> implements Chain<TIn, TOut, TResult>
{
    FunctionSingle<TIn, TResult> value;

    public CallJoinChain(FunctionSingle<TIn, TResult> value)
    {
        this.value = value;
    }

    @Override
    public <NIn> Chain<NIn, TIn, TResult> join(FunctionSingle<NIn, TIn> out)
    {
        return new CallJoinChain<NIn, TIn, TResult>(v -> value.call(out.call(v)));
    }

    @Override
    public TResult close(TIn in)
    {
        return value.call(in);
    }
}