package jena.engine.common;

public class CallChain<TIn, TOut> implements Chain<TIn, TOut>
{
    FunctionSingle<TIn, TOut> value;

    public CallChain(FunctionSingle<TIn, TOut> value)
    {
        this.value = value;
    }

    @Override
    public <NOut> Chain<TOut, NOut> join(FunctionSingle<TOut, NOut> out)
    {
        return new CallChain<TOut, NOut>(out);    
    }
    
    @Override
    public TOut close(TIn in)
    {
        return value.call(in);
    }
}