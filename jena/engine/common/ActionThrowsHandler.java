package jena.engine.common;

public class ActionThrowsHandler<TError extends Throwable> implements Action
{
    ActionThrows<TError> action;
    ActionSingle<Throwable> errorCase;

    public ActionThrowsHandler(ActionThrows<TError> action, ActionSingle<Throwable> errorCase)
    {
        this.action = action;
        this.errorCase = errorCase;
    }

    @Override
    public void call()
    {    
        try
        {
            action.call();
        }
        catch(Throwable error)
        {
            errorCase.call(error);
        }
    }
}