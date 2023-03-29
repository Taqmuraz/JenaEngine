package jena.engine.math;

public final class Vector2fValueFloat implements Vector2f
{
    private ValueFloat x;
    private ValueFloat y;
    
    public Vector2fValueFloat(ValueFloat x, ValueFloat y)
    {
        this.x = x;
        this.y = y;
    }

    @Override
    public void accept(Vector2fAcceptor acceptor)
    {
        x.accept(x -> y.accept(y -> acceptor.call(x, y)));
    }
}