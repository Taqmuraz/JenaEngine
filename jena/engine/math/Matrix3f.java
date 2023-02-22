package jena.engine.math;

public interface Matrix3f
{
    void accept(Matrix3fAcceptor acceptor);

    default Matrix3f translate(Vector2f t)
    {
        return new Matrix3fMul(this, new Matrix3fTranslation(t));
    }
    default Matrix3f multiply(Matrix3f matrix)
    {
        return new Matrix3fMul(this, matrix);
    }
    default Matrix3f scale(Vector2f scale)
    {
        return new Matrix3fMul(this, new Matrix3fScale(scale));
    }
    default Matrix3f inverse()
    {
        return new Matrix3fInversed(this);
    }
    default Matrix3f transpone()
    {
        return new Matrix3fTransponed(this);
    }
    default Matrix3fStruct struct()
    {
        return new Matrix3fStruct(this);
    }
    default ValueFloat det()
    {
        return new Matrix3fDet(this);
    }
}