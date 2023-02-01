package jena.engine.math;

public class Matrix3fTransform extends Matrix3fRotation
{
    Vector2f position;
    Vector2f scale;

    public Matrix3fTransform(Vector2f position, ValueFloat rotation)
    {
        super(rotation);
        this.position = position;
        this.scale = a -> a.call(1f, 1f);
    }
    public Matrix3fTransform(Vector2f position, Vector2f scale, ValueFloat rotation)
    {
        super(rotation);
        this.position = position;
        this.scale = scale;
    }

    @Override
    public void accept(Matrix3fAcceptor acceptor)
    {
        super.accept(base -> position.accept((px, py) -> scale.accept((sx, sy) -> acceptor.call(index ->
        {
            switch(index)
            {
                case 0: return base.at(0) * sx;
                case 1: return base.at(1) * sx;
                case 3: return base.at(3) * sy;
                case 4: return base.at(4) * sy;
                case 6: return px;
                case 7: return py;
                case 8: return 1f;
                default: return 0f;
            }
        }))));
    }
}