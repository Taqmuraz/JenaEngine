package jena.engine.math;

public class Vector2fStruct implements Vector2f
{
    public float x, y;
    Vector2fAcceptor acceptor;

    public Vector2fStruct()
    {
        acceptor = this::apply;
    }

    public Vector2fStruct(float x, float y)
    {
        this.x = x;
        this.y = y;
        acceptor = this::apply;
    }

    public Vector2fStruct(Vector2f source)
    {
        source.accept((x, y) ->
        {
            this.x = x;
            this.y = y;
        });
        acceptor = this::apply;
    }

    public void apply(Vector2f vector)
    {
        vector.accept(acceptor);
    }

    public void apply(float x, float y)
    {
        this.x = x;
        this.y = y;
    }

    @Override
    public void accept(Vector2fAcceptor acceptor) 
    {
        acceptor.call(x, y);
    }

    public float length()
    {
        return (float)Math.sqrt(x * x + y * y);
    }
    @Override
    public Vector2f clone()
    {
        return new Vector2fStruct(x, y);
    }

    @Override
    public String toString()
    {
        return String.format("(%f, %f)", x, y);
    }
}