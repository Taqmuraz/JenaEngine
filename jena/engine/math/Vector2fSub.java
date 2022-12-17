package jena.engine.math;

public final class Vector2fSub extends Vector2fStruct
{
    public Vector2fSub(Vector2f a, Vector2f b)
    {
        a.accept((ax, ay) -> b.accept((bx, by) -> 
        {
            x = ax - bx;
            y = ay - by;
        }));
    }
}