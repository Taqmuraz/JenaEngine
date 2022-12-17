package jena.engine.math;

public final class Vector3fAdd extends Vector3fStruct
{
    public Vector3fAdd(Vector3f a, Vector3f b)
    {
        a.accept((ax, ay, az) -> b.accept((bx, by, bz) -> 
        {
            x = ax + bx;
            y = ay + by;
            z = az + bz;
        }));
    }
}