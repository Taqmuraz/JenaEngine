package jena.engine.math;

public class Vector3fStruct implements Vector3f
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

    @Override
    public void accept(Vector3fAcceptor acceptor)
    {
        acceptor.call(x, y, z);
    }

    @Override
    public float length()
    {
        return (float)Math.sqrt(x * x + y * y + z * z);
    }
}