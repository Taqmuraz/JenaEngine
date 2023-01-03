package jena.engine.math;

public class Matrix3fOrtho extends Matrix3fStruct
{
    public Matrix3fOrtho(Vector2f size, float scale)
    {
        size.accept((x, y) ->
        {
            elements[0] = y / x;
        });
    }
}