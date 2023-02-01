package jena.engine.math;

public class Matrix3fRect implements Matrix3f
{
    Rectf rect;

    public Matrix3fRect(Rectf rect)
    {
        this.rect = rect;
    }

    @Override
    public void accept(Matrix3fAcceptor acceptor)
    {
        rect.accept((x, y, w, h) -> acceptor.call(i ->
        {
            switch(i)
            {
                case 0: return w;
                case 4: return h;
                default:
                case 1:
                case 2:
                case 3:
                case 5: return 0f;
                case 6: return x;
                case 7: return y;
                case 8: return 1f;
            }
        }));
    }
}