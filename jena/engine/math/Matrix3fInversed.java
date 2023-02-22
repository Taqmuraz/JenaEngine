package jena.engine.math;

public final class Matrix3fInversed implements Matrix3f
{
    private Matrix3f source;

    public Matrix3fInversed(Matrix3f source)
    {
        this.source = source;
    }

    private static float step(Matrix3fElements s, int i, int j, int si, int sj)
    {
        return s.at(((i + si) % 3) + ((j + sj) % 3) * 3);
    }

    @Override
    public void accept(Matrix3fAcceptor acceptor)
    {
        source.det().accept(det -> source.accept(s -> acceptor.call(index ->
        {
            // because adj matrix has to be transponed. implicitly transponing here
            int i = index / 3;
            int j = index % 3;

            return (step(s, i, j, 1, 1) * step(s, i, j, 2, 2) - step(s, i, j, 2, 1) * step(s, i, j, 1, 2)) / det;
        })));
    }
}