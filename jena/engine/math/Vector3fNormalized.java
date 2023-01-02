package jena.engine.math;

public final class Vector3fNormalized extends Vector3fStruct
{
    public Vector3fNormalized(Vector3f source)
    {
        Vector3fStruct struct = new Vector3fStruct(source);
        float length = struct.length();
        if (length != 0)
        {
            x = struct.x / length;
            y = struct.y / length;
            z = struct.z / length;
        }
        else x = y = z = 0f;
    }
}