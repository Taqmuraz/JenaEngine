package jena.engine.math;

public class Matrix3fStruct implements Matrix3f
{
    Vector3fAcceptor col0;
    Vector3fAcceptor col1;
    Vector3fAcceptor col2;
    Matrix3fElements at;

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
        this();
        Matrix3fElements sourceElements = source.elements();
        for(int i = 0; i < 9; i++)
        {
            elements[i] = sourceElements.at(i);
        }
    }

    public Matrix3fStruct()
    {
        elements = new float[9];
        elements[0] = elements[4] = elements[8] = 1f;

        at = index -> elements[index];

        col0 = (x, y, z) -> 
        {
            elements[0] = x;
            elements[1] = y;
            elements[2] = z;
        };
        col1 = (x, y, z) -> 
        {
            elements[3] = x;
            elements[4] = y;
            elements[5] = z;
        };
        col2 = (x, y, z) -> 
        {
            elements[6] = x;
            elements[7] = y;
            elements[8] = z;
        };
    }

    public Matrix3fStruct(Vector3f column0, Vector3f column1, Vector3f column2)
    {
        elements = new float[9];

        column0.accept(col0);
        column1.accept(col1);
        column2.accept(col2);
    }

    @Override
    public Matrix3fElements elements()
    {
        return at;
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