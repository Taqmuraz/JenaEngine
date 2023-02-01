package jena.engine.math;

import jena.environment.variable.DimensionVariable;

public class DimensionStruct implements DimensionVariable
{
    public int width;
    public int height;

    public DimensionStruct(Dimension dimension)
    {
        dimension.accept((w, h) ->
        {
            this.width = w;
            this.height = h;
        });
    }

    @Override
    public void accept(DimensionAcceptor acceptor)
    {
        acceptor.call(width, height);
    }
}