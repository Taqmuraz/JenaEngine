package jena.engine.math;

public class Matrix3fScale implements Matrix3f
{
    Vector2f scale;
    
    public Matrix3fScale(Vector2f scale)
    {
        this.scale = scale;
    }
    
    @Override
    public Matrix3fElements elements()
    {
        Vector2fStruct s = new Vector2fStruct(scale);
        return index ->
        {
            switch(index)
            {
                case 0: return s.x;
                case 4: return s.y;
                case 8: return 1f;
                default: return 0f;
            }
        };
    }
}