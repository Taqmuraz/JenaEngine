package jena.engine.math;

public class Matrix3fMul implements Matrix3f
{
    private Matrix3f a;
    private Matrix3f b;
    Matrix3fElements ea;
    Matrix3fElements eb;
    Matrix3fElements elements;
    
    public Matrix3fMul(Matrix3f a, Matrix3f b)
    {
        this.a = a;
        this.b = b;

        elements = index ->
        {
            int i = index % 3;
            int j = index / 3;
            int startA = i;
            int startB = j * 3;

            return
                ea.at(startA) * eb.at(startB) +
                ea.at(startA + 3) * eb.at(startB + 1) +
                ea.at(startA + 6) * eb.at(startB + 2);
        };
    }

    @Override
    public Matrix3fElements elements()
    {
        ea = a.elements();
        eb = b.elements();
        return elements;
    }
}