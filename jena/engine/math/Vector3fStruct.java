package jena.engine.math;

public final class Vector3fStruct implements Vector3f
{
    public float x, y, z;
    
    public Vector3fStruct()
    {
    }

    public Vector3fStruct(float x, float y, float z)
    {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    public Vector3fStruct(Vector3f source)
    {
        source.accept((x, y, z) ->
        {
            this.x = x;
            this.y = y;
            this.z = z;
        });
    }

    @Override
    public void accept(Vector3fAcceptor acceptor)
    {
        acceptor.call(x, y, z);
    }

    @Override
    public Vector3f clone()
    {
        return new Vector3fStruct(x, y, z);
    }

    @Override
    public String toString()
    {
        return String.format("(%f, %f, %f)", x, y, z);
    }
}