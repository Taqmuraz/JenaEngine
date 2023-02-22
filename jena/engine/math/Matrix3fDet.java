package jena.engine.math;

public final class Matrix3fDet implements ValueFloat
{
    private Matrix3f source;

    public Matrix3fDet(Matrix3f source)
    {
        this.source = source;
    }

    @Override
    public void accept(FloatAcceptor acceptor)
    {
        source.accept(s ->
        {
            acceptor.call(
                s.at(0) * s.at(4) * s.at(8) - 
                s.at(0) * s.at(5) * s.at(7) -
                s.at(1) * s.at(3) * s.at(8) +
                s.at(2) * s.at(3) * s.at(7) +
                s.at(1) * s.at(5) * s.at(6) -
                s.at(2) * s.at(4) * s.at(6));
        });
    }
}