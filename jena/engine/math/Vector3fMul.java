package jena.engine.math;

public final class Vector3fMul extends Vector3fStruct
{
    public Vector3fMul(Vector3f a, Vector3f b)
    {
        a.accept((ax, ay, az) -> b.accept((bx, by, bz) -> 
        {
            x = ax * bx;
            y = ay * by;
            z = az * bz;
        }));
    }
    public Vector3fMul(Vector3f a, float b)
    {
        a.accept((ax, ay, az) ->
        {
            x = ax * b;
            y = ay * b;
            z = az * b;
        });
    }
}