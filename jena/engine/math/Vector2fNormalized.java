package jena.engine.math;

public final class Vector2fNormalized extends Vector2fStruct
{
    public Vector2fNormalized(Vector2f source)
    {
        source.accept((sx, sy) ->
        {
            float length = source.length();
            if (length != 0)
            {
                x = sx / length;
                y = sy / length;
            }
            else x = y = 0f;
        });
    }
}