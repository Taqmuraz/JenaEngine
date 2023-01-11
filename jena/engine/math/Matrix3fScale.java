package jena.engine.math;

public class Matrix3fScale implements Matrix3f
{
    Vector2f scale;
    Vector2fStruct scaleStruct = new Vector2fStruct();
    
    public Matrix3fScale(Vector2f scale)
    {
        this.scale = scale;
    }
    
    @Override
    public Matrix3fElements elements()
    {
        scaleStruct.apply(scale);
        return index ->
        {
            switch(index)
            {
                case 0: return scaleStruct.x;
                case 4: return scaleStruct.y;
                case 8: return 1f;
                default: return 0f;
            }
        };
    }
}