package jena.engine.math;

public final class Vector3fNormalized extends Vector3fStruct
{
    public Vector3fNormalized(Vector3f source)
    {
        source.accept((sx, sy, sz) ->
        {
            float length = source.length();
            if (length != 0)
            {
                x = sx / length;
                y = sy / length;
                z = sz / length;
            }
            else x = y = z = 0f;
        });
    }
}