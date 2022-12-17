package jena.engine.math;

public final class Vector3fSub extends Vector3fStruct
{
    public Vector3fSub(Vector3f a, Vector3f b)
    {
        a.accept((ax, ay, az) -> b.accept((bx, by, bz) -> 
        {
            x = ax - bx;
            y = ay - by;
            z = az - bz;
        }));
    }
}