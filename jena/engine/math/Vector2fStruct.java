package jena.engine.math;

public class Vector2fStruct implements Vector2f
{
    public float x, y;

    public Vector2fStruct()
    {
    }

    public Vector2fStruct(float x, float y)
    {
        this.x = x;
        this.y = y;
    }

    @Override
    public void accept(Vector2fAcceptor acceptor) 
    {
        acceptor.call(x, y);
    }

    @Override
    public float length()
    {
        return (float)Math.sqrt(x * x + y * y);
    }
}