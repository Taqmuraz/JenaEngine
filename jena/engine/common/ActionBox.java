package jena.engine.common;

public class ActionBox<TResult> implements Action
{
    TResult result;
    Function<TResult> function;

    public ActionBox(Function<TResult> function)
    {
        this.function = function;
    }

    @Override
    public void call()
    {
        result = function.call();
    }
    public TResult result()
    {
        return result;
    }
}