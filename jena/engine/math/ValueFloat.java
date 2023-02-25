package jena.engine.math;

public interface ValueFloat
{
    void accept(FloatAcceptor acceptor);

    default ValueFloat add(ValueFloat v)
    {
        return a -> this.accept(x -> v.accept(y -> a.call(x + y)));
    }
    default ValueFloat sub(ValueFloat v)
    {
        return a -> this.accept(x -> v.accept(y -> a.call(x - y)));
    }
    default ValueFloat mul(ValueFloat v)
    {
        return a -> this.accept(x -> v.accept(y -> a.call(x * y)));
    }
    default ValueFloat div(ValueFloat v)
    {
        return a -> this.accept(x -> v.accept(y -> a.call(x / y)));
    }
    default ValueFloat add(float v)
    {
        return a -> this.accept(x -> a.call(x + v));
    }
    default ValueFloat sub(float v)
    {
        return a -> this.accept(x -> a.call(x - v));
    }
    default ValueFloat mul(float v)
    {
        return a -> this.accept(x -> a.call(x * v));
    }
    default ValueFloat div(float v)
    {
        return a -> this.accept(x -> a.call(x / v));
    }
    default ValueFloat sin()
    {
        return a -> this.accept(v -> a.call((float)Math.sin(v)));
    }
    default ValueFloat cos()
    {
        return a -> this.accept(v -> a.call((float)Math.cos(v)));
    }
}