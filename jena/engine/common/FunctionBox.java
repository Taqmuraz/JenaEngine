package jena.engine.common;

public class FunctionBox<TResult> implements Function<TResult>
{
    ActionSingle<ActionSingle<TResult>> function;
    Function<TResult> defaultResult;
    public FunctionBox(ActionSingle<ActionSingle<TResult>> function, Function<TResult> defaultResult)
    {
        this.function = function;
        this.defaultResult = defaultResult;
    }
    @Override
    public TResult call()
    {
        Box<TResult> box = new Box<TResult>(defaultResult);
        function.call(box::write);
        return box.read();
    }
}