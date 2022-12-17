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
    public Vector2fMul(Vector2f a, float b)
    {
        a.accept((ax, ay) ->
        {
            x = ax * b;
            y = ay * b;
        });
    }
}