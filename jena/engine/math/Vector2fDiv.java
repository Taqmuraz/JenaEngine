package jena.engine.math;

public final class Vector2fDiv implements Vector2f
{
    private Vector2f result;

    public Vector2fDiv(Vector2f a, Vector2f b)
    {
        result = r -> a.accept((ax, ay) -> b.accept((bx, by) -> 
        {
            float x = bx != 0f ? (ax / bx) : 0f;
            float y = by != 0f ? (ay / by) : 0f;
            r.call(x, y);
        }));
    }
    public Vector2fDiv(Vector2f a, ValueFloat b)
    {
        result = r -> a.accept((ax, ay) ->
        {
            float d = b.read();
            if (d != 0)
                r.call(ax / d, ay / d);
            else
                r.call(0f, 0f);
        });
    }

    @Override
    public void accept(Vector2fAcceptor acceptor)
    {
        result.accept(acceptor);
    }
}