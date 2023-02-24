package jena.engine.math;

public class RectfNormalized implements Rectf
{
    Rectf source;

    public RectfNormalized(Rectf source)
    {
        this.source = source;
    }

    @Override
    public void accept(RectfAcceptor acceptor)
    {
        source.accept((x, y, w, h) ->
        {
            if (w < 0)
            {
                x += w;
                w = Math.abs(w);
            }
            if (h < 0)
            {
                y += h;
                h = Math.abs(h);
            }
            acceptor.call(x, y, w, h);
        });
    }
}