package jena.engine.math;

public class Matrix3fIdentity implements Matrix3f, Matrix3fElements
{
    public static final Matrix3f identity = new Matrix3fIdentity();

    @Override
    public Matrix3fElements elements()
    {
        return this;
    }

    @Override
    public float at(int index)
    {
        switch(index)
        {
            case 0:
            case 4:
            case 8:
                return 1f;
            default:
                return 0f;
        }
    }
}