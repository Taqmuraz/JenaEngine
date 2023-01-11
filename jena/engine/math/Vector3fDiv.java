package jena.engine.math;

public final class Vector3fDiv implements Vector3f
{
    Vector3f result;
    public Vector3fDiv(Vector3f a, Vector3f b)
    {
        result = r -> a.accept((ax, ay, az) -> b.accept((bx, by, bz) -> 
        {
            float x = bx != 0f ? (ax / bx) : 0f;
            float y = by != 0f ? (ay / by) : 0f;
            float z = bz != 0f ? (az / bz) : 0f;
            r.call(x, y, z);
        }));
    }
    public Vector3fDiv(Vector3f a, float b)
    {
        result = r -> a.accept((ax, ay, az) ->
        {
            if (b != 0f)
                r.call(ax / b, ay / b, az / b);
            else
                r.call(0f, 0f, 0f);
        });
    }
    
    @Override
    public void accept(Vector3fAcceptor acceptor)
    {
        result.accept(acceptor);
    }
}