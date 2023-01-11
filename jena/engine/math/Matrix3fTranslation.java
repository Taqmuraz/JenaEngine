package jena.engine.math;

public class Matrix3fTranslation implements Matrix3f
{
    Vector2f translation;
    Vector2fStruct translationStruct = new Vector2fStruct();
    Matrix3fElements elements;
    
    public Matrix3fTranslation(Vector2f translation)
    {
        this.translation = translation;
        elements = index ->
        {
            switch(index)
            {
                case 6: return translationStruct.x;
                case 7: return translationStruct.y;
                case 0:
                case 4:
                case 8: return 1f;
                default: return 0f;
            }
        };
    }
    
    @Override
    public Matrix3fElements elements()
    {
        translationStruct.apply(translation);
        return elements;
    }
}