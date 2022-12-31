package jena.engine.common;

public class FunctionDoubleThrowsHandler<TArg0, TArg1, TResult, TError extends Throwable> implements FunctionDouble<TArg0, TArg1, TResult>
{
    FunctionDoubleThrows<TArg0, TArg1, TResult, TError> function;
    FunctionTriple<TArg0, TArg1, Throwable, TResult> errorCase;

    public FunctionDoubleThrowsHandler(FunctionDoubleThrows<TArg0, TArg1, TResult, TError> function, FunctionTriple<TArg0, TArg1, Throwable, TResult> errorCase)
    {
        this.function = function;
        this.errorCase = errorCase;
    }

    @Override
    public TResult call(TArg0 arg0, TArg1 arg1)
    {
        try
        {
            return function.call(arg0, arg1);
        }
        catch(Throwable error)
        {
            return errorCase.call(arg0, arg1, error);
        }
    }
}