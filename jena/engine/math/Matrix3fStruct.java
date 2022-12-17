package jena.engine.math;

public class Matrix3fStruct implements Matrix3f
{
    public float[] elements;

    private Matrix3fStruct(float[] elements)
    {
        this.elements = elements.clone();
    }
    public Matrix3fStruct()
    {
        elements = new float[9];
        elements[0] = elements[4] = elements[8] = 1f;
    }
    public Matrix3fStruct(Vector3f column0, Vector3f column1, Vector3f column2)
    {
        elements = new float[9];

        column0.accept((x, y, z) -> 
        {
            elements[0] = x;
            elements[1] = y;
            elements[2] = z;
        });
        column1.accept((x, y, z) -> 
        {
            elements[3] = x;
            elements[4] = y;
            elements[5] = z;
        });
        column2.accept((x, y, z) -> 
        {
            elements[6] = x;
            elements[7] = y;
            elements[8] = z;
        });
    }

    @Override
    public void accept(Matrix3fAcceptor acceptor)
    {
        acceptor.call(elements);
    }

    @Override
    public Matrix3f clone()
    {
        return new Matrix3fStruct(elements);
    }
}