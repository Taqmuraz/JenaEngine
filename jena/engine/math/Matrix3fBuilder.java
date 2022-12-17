package jena.engine.math;

public final class Matrix3fBuilder
{
    Matrix3f result;

    public Matrix3fBuilder()
    {
        result = new Matrix3fStruct();
    }
    public Matrix3fBuilder(Matrix3f source)
    {
        this.result = source.clone();
    }
    public Matrix3fBuilder multiply(Matrix3f matrix)
    {
        result = new Matrix3fMul(result, matrix);
        return this;
    }
    public Matrix3fBuilder translate(Vector2f vector)
    {
        result.accept(elements -> vector.accept((x, y) ->
        {
            elements[6] += x;
            elements[7] += y;
        }));
        return this;
    }
    public Matrix3fBuilder scale(Vector2f scale)
    {
        result.accept(elements -> scale.accept((x, y) ->
        {
            elements[0] *= x;
            elements[1] *= x;

            elements[3] *= y;
            elements[4] *= y;
        }));
        return this;
    }
}