package jena.engine.math;

public class Matrix3fStruct implements Matrix3f
{
    public float[] elements;
    /*
        elements layout :

        [ 0 3 6 ] -- line 0
        [ 1 4 7 ] -- line 1
        [ 2 5 8 ] -- line 2

        [ 0 1 2 ] -- column 0
        [ 3 4 5 ] -- column 1
        [ 6 7 8 ] -- column 2
    */

    public Matrix3fStruct(Matrix3f source)
    {
        source.accept(elements -> this.elements = elements.clone());
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
    public String toString()
    {
        return String.format("matrix struct\n\t[%f, %f, %f]\n\t[%f, %f, %f]\n\t[%f, %f, %f]",
            elements[0],
            elements[1],
            elements[2],
            elements[3],
            elements[4],
            elements[5],
            elements[6],
            elements[7],
            elements[8]);
    }
}