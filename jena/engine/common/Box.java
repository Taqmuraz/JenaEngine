package jena.engine.common;

public class Box<TValue>
{
    private Function<TValue> value;

    public Box(Function<TValue> value)
    {
        this.value = value;
    }
    public void write(TValue value)
    {
        this.value = () -> value;
    }
    public void write(Function<TValue> value)
    {
        this.value = value;
    }
    public TValue read()
    {
        return value.call();
    }
}