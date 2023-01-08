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
        this.result = new Matrix3fStruct(source);
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
            elements[6] += x * (elements[0] + elements[3]);
            elements[7] += y * (elements[1] + elements[4]);
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
    public Matrix3f build()
    {
        return result;
    }
}