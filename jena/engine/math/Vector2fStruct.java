package jena.engine.math;

public final class Vector2fStruct implements Vector2f
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

    public Vector2fStruct(Vector2f source)
    {
        source.accept((x, y) ->
        {
            this.x = x;
            this.y = y;
        });
    }

    @Override
    public void accept(Vector2fAcceptor acceptor) 
    {
        acceptor.call(x, y);
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