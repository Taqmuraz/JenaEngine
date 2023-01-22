package jena.engine.common;

public class Box<TValue>
{
    private TValue value;
    private boolean hasValue;
    private Function<TValue> defaultValue;

    public Box(Function<TValue> defaultValue)
    {
        this.defaultValue = defaultValue;
    }
    public void write(TValue value)
    {
        this.value = value;
        hasValue = true;
    }
    public TValue read()
    {
        return hasValue ? value : defaultValue.call();
    }
}