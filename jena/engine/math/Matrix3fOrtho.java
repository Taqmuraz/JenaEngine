package jena.engine.math;

public class Matrix3fOrtho extends Matrix3fStruct
{
    public Matrix3fOrtho(Vector2f size, float scale)
    {
        size.accept((x, y) ->
        {
            float dScale = 1f / scale;
            elements[0] = (y / x) * dScale;
            elements[4] = dScale;
        });
    }
}