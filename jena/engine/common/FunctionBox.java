package jena.engine.common;

public class FunctionBox<TResult>
{
    TResult result;
    ActionSingle<ActionSingle<TResult>> action;

    public FunctionBox(ActionSingle<ActionSingle<TResult>> action)
    {
        this.action = action;
    }

    public TResult result()
    {
        action.call(r -> result = r);
        return result;
    }
}