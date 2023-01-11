package jena.engine.math;

public final class Vector2fMul extends Vector2fStruct
{
    public Vector2fMul(Vector2f a, Vector2f b)
    {
        a.accept((ax, ay) -> b.accept((bx, by) -> 
        {
            x = ax * bx;
            y = ay * by;
        }));
    }
    public Vector2fMul(Vector2f a, ValueFloat b)
    {
        a.accept((ax, ay) ->
        {
            float m = b.read();
            x = ax * m;
            y = ay * m;
        });
    }
}