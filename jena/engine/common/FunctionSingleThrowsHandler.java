package jena.engine.common;

public class FunctionSingleThrowsHandler<TArg, TResult, TError extends Throwable> implements FunctionSingle<TArg, TResult>
{
    FunctionSingleThrows<TArg, TResult, TError> function;
    FunctionDouble<TArg, Throwable, TResult> errorCase;

    public FunctionSingleThrowsHandler(FunctionSingleThrows<TArg, TResult, TError> function, FunctionDouble<TArg, Throwable, TResult> errorCase)
    {
        this.function = function;
        this.errorCase = errorCase;
    }

    @Override
    public TResult call(TArg arg)
    {
        try
        {
            return function.call(arg);
        }
        catch(Throwable error)
        {
            return errorCase.call(arg, error);
        }
    }
}