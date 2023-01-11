package jena.engine.math;

public final class Vector3fMul implements Vector3f
{
    Vector3f result;

    public Vector3fMul(Vector3f a, Vector3f b)
    {
        result = r -> a.accept((ax, ay, az) -> b.accept((bx, by, bz) -> 
        {
            float x = ax * bx;
            float y = ay * by;
            float z = az * bz;
            r.call(x, y, z);
        }));
    }
    public Vector3fMul(Vector3f a, float b)
    {
        result = r -> a.accept((ax, ay, az) ->
        {
            float x = ax * b;
            float y = ay * b;
            float z = az * b;
            r.call(x, y, z);
        });
    }
    public Vector3fMul(Vector3f a, Matrix3f b)
    {
        result = r -> a.accept((ax, ay, az) ->
        {
            Matrix3fElements e = b.elements();
            float x = ax * e.at(0) + ay * e.at(3) + az * e.at(6);
            float y = ax * e.at(1) + ay * e.at(4) + az * e.at(7);
            float z = ax * e.at(2) + ay * e.at(5) + az * e.at(8);
            r.call(x, y, z);
        });
    }
    @Override
    public void accept(Vector3fAcceptor acceptor)
    {
        result.accept(acceptor);
    }
}