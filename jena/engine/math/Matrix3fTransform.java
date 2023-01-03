package jena.engine.math;

public class Matrix3fTransform extends Matrix3fRotation
{
    public Matrix3fTransform(Vector2f position, float rotation)
    {
        super(rotation);
        position.accept((x, y) ->
        {
            elements[6] = x;
            elements[7] = y;
        });
    }
}