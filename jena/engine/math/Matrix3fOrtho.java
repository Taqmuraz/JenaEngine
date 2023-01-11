package jena.engine.math;

public class Matrix3fOrtho extends Matrix3fStruct
{
    private Vector2f clipSize;
    private ValueFloat worldScale;

    public Matrix3fOrtho(Vector2f clipSize, ValueFloat worldScale)
    {
        this.clipSize = clipSize;
        this.worldScale = worldScale;
    }

    @Override
    public Matrix3fElements elements()
    {
        Vector2fStruct size = new Vector2fStruct(clipSize);
        float dScale = 1f / worldScale.read();
        if (size.x > size.y)
        {
            return index ->
            {
                switch(index)
                {
                    case 0: return (size.y / size.x) * dScale;
                    case 4: return dScale;
                    case 8: return 1f;
                    default: return 0f;
                }
            };
        }
        else
        {
            return index ->
            {
                switch(index)
                {
                    case 0: return dScale;
                    case 4: return (size.x / size.y) * dScale;
                    case 8: return 1f;
                    default: return 0f;
                }
            };
        }
    }
}