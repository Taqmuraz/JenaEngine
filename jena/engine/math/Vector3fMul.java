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
    public Vector3fMul(Vector3f a, Matrix3f b)
    {
        b.accept(elements -> a.accept((ax, ay, az) ->
        {
            x = ax * elements[0] + ay * elements[3] + az * elements[6];
            y = ax * elements[1] + ay * elements[4] + az * elements[7];
            z = ax * elements[2] + ay * elements[5] + az * elements[8];
        }));
    }
    public float dot()
    {
        return x + y + z;
    }
}