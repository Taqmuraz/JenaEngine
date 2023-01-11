package jena.engine.math;

public final class Matrix3fBuilder
{
    Matrix3f result;

    public Matrix3fBuilder()
    {
        result = Matrix3fIdentity.identity;
    }
    public Matrix3fBuilder(Matrix3f source)
    {
        this.result = source;
    }
    public Matrix3fBuilder multiply(Matrix3f matrix)
    {
        result = new Matrix3fMul(result, matrix);
        return this;
    }
    public Matrix3fBuilder translate(Vector2f vector)
    {
        result = new Matrix3fMul(result, new Matrix3fTranslation(vector));
        return this;
    }
    public Matrix3fBuilder scale(Vector2f scale)
    {
        result = new Matrix3fMul(result, new Matrix3fScale(scale));
        return this;
    }
    public Matrix3f build()
    {
        return result;
    }
    public Matrix3f struct()
    {
        return new Matrix3fStruct(result);
    }
}