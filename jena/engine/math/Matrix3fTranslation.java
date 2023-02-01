package jena.engine.math;

public class Matrix3fTranslation implements Matrix3f
{
    Vector2f translation;
    
    public Matrix3fTranslation(Vector2f translation)
    {
        this.translation = translation;
    }
    
    @Override
    public void accept(Matrix3fAcceptor acceptor)
    {
        translation.accept((tx, ty) -> acceptor.call(index ->
        {
            switch(index)
            {
                case 6: return tx;
                case 7: return ty;
                case 0:
                case 4:
                case 8: return 1f;
                default: return 0f;
            }
        }));
    }
}