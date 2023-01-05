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
    public Matrix3fTransform(float x, float y, float rotation)
    {
        super(rotation);
        elements[6] = x;
        elements[7] = y;
    }
    public Matrix3fTransform(float posX, float posY, float scaleX, float scaleY, float rotation)
    {
        super(rotation);
        elements[6] = posX;
        elements[7] = posY;
        elements[0] *= scaleX;
        elements[1] *= scaleX;
        elements[3] *= scaleY;
        elements[4] *= scaleY;
    }
}