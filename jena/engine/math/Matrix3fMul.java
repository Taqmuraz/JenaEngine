package jena.engine.math;

public class Matrix3fMul extends Matrix3fStruct
{
    public Matrix3fMul(Matrix3f a, Matrix3f b)
    {
        a.accept(elementsA -> b.accept(elementsB ->
        {
            for (int i = 0; i < 3; i++)
            {
                for (int j = 0; j < 3; j++)
                {
                    int startA = i;
                    int startB = j * 3;

                    elements[j * 3 + i] =
                        elementsA[startA] * elementsB[startB] +
                        elementsA[startA + 3] * elementsB[startB + 1] +
                        elementsA[startA + 6] * elementsB[startB + 2];
                }
            }
        }));
    }
}