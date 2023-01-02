package jena.engine.math;

public final class Vector2fNormalized extends Vector2fStruct
{
    public Vector2fNormalized(Vector2f source)
    {
        Vector2fStruct struct = new Vector2fStruct(source);
        float length = struct.length();
        if (length != 0)
        {
            x = struct.x / length;
            y = struct.y / length;
        }
        else x = y = 0f;
    }
}