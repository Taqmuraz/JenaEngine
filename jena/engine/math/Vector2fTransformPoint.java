package jena.engine.math;

public class Vector2fTransformPoint implements Vector2f
{
    private Matrix3f transform;
    private Vector2f point;

    public Vector2fTransformPoint(Matrix3f transform, Vector2f point)
    {
        this.transform = transform;
        this.point = point;
    }

    @Override
    public void accept(Vector2fAcceptor acceptor)
    {
        point.accept((px, py) -> transform.accept(e ->
        {
            float x = px * e.at(0) + py * e.at(3) + e.at(6);
            float y = px * e.at(1) + py * e.at(4) + e.at(7);
            acceptor.call(x, y);
        }));
    }
}