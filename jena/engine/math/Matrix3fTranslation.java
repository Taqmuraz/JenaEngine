package jena.engine.math;

public class Matrix3fTranslation implements Matrix3f
{
    Vector2f translation;
    
    public Matrix3fTranslation(Vector2f translation)
    {
        this.translation = translation;
    }
    
    @Override
    public Matrix3fElements elements()
    {
        Vector2fStruct t = new Vector2fStruct(translation);
        return index ->
        {
            switch(index)
            {
                case 6: return t.x;
                case 7: return t.y;
                case 0:
                case 4:
                case 8: return 1f;
                default: return 0f;
            }
        };
    }
}