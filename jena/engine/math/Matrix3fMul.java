package jena.engine.math;

public class Matrix3fMul implements Matrix3f
{
    private Matrix3f a;
    private Matrix3f b;
    
    public Matrix3fMul(Matrix3f a, Matrix3f b)
    {
        this.a = a;
        this.b = b;
    }

    @Override
    public Matrix3fElements elements()
    {
        Matrix3fElements ea = a.elements();
        Matrix3fElements eb = b.elements();
        return index ->
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
}