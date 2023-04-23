package jena.engine.math;

public class Matrix3fViewport implements Matrix3f
{
    float halfWidth;
    float halfHeight;
    public Matrix3fViewport(Vector2f size)
    {
        size.accept((width, height) ->
        {
            this.halfWidth = width * 0.5f;
            this.halfHeight = height * 0.5f;
        });
    }
    @Override
    public void accept(Matrix3fAcceptor acceptor)
    {
        acceptor.call(index ->
        {
            switch(index)
            {
                case 0: return halfWidth;
                case 4: return -halfHeight;
                case 6: return halfWidth;
                case 7: return halfHeight;
                case 8: return 1f;
                default: return 0f;
            }
        });
    }
}