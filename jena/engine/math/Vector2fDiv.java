package jena.engine.math;

public final class Vector2fDiv extends Vector2fStruct
{
    public Vector2fDiv(Vector2f a, Vector2f b)
    {
        a.accept((ax, ay) -> b.accept((bx, by) -> 
        {
            x = bx != 0f ? (ax / bx) : 0f;
            y = by != 0f ? (ay / by) : 0f;
        }));
    }
    public Vector2fDiv(Vector2f a, float b)
    {
        a.accept((ax, ay) ->
        {
            if (b != 0)
            {
                x = ax / b;
                y = ay / b;
            }
            else x = y = 0f;
        });
    }
}