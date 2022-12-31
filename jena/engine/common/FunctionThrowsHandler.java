package jena.engine.common;

public class FunctionThrowsHandler<TResult, TError extends Throwable> implements Function<TResult>
{
    FunctionThrows<TResult, TError> function;
    FunctionSingle<Throwable, TResult> errorCase;

    public FunctionThrowsHandler(FunctionThrows<TResult, TError> function, FunctionSingle<Throwable, TResult> errorCase)
    {
        this.function = function;
        this.errorCase = errorCase;
    }

    @Override
    public TResult call()
    {
        try
        {
            return function.call();
        }
        catch(Throwable error)
        {
            return errorCase.call(error);
        }
    }
}