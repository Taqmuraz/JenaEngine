package jena.engine.math;

public class Matrix3fScale implements Matrix3f
{
    Vector2f scale;
    
    public Matrix3fScale(Vector2f scale)
    {
        this.scale = scale;
    }
    
    @Override
    public void accept(Matrix3fAcceptor acceptor)
    {
        scale.accept((x, y) -> acceptor.call(index ->
        {
            switch(index)
            {
                case 0: return x;
                case 4: return y;
                case 8: return 1f;
                default: return 0f;
            }
        }));
    }
}