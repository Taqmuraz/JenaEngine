package jena.engine.math;

public final class Vector3fDiv extends Vector3fStruct
{
    public Vector3fDiv(Vector3f a, Vector3f b)
    {
        a.accept((ax, ay, az) -> b.accept((bx, by, bz) -> 
        {
            x = bx != 0f ? (ax / bx) : 0f;
            y = by != 0f ? (ay / by) : 0f;
            z = bz != 0f ? (az / bz) : 0f;
        }));
    }
    public Vector3fDiv(Vector3f a, float b)
    {
        a.accept((ax, ay, az) ->
        {
            if (b != 0f)
            {
                x = ax / b;
                y = ay / b;
                z = az / b;
            }
            else x = y = z = 0f;
        });
    }
}