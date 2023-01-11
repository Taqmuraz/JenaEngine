package jena.engine.math;

public class Matrix3fOrtho extends Matrix3fStruct
{
    private Vector2f clipSize;
    private ValueFloat worldScale;
    private Vector2fStruct clipSizeStruct = new Vector2fStruct();
    private Matrix3fElements elementsW;
    private Matrix3fElements elementsH;
    private float dScale;

    public Matrix3fOrtho(Vector2f clipSize, ValueFloat worldScale)
    {
        this.clipSize = clipSize;
        this.worldScale = worldScale;

        elementsW = index ->
        {
            switch(index)
            {
                case 0: return (clipSizeStruct.y / clipSizeStruct.x) * dScale;
                case 4: return dScale;
                case 8: return 1f;
                default: return 0f;
            }
        };
        elementsH = index ->
        {
            switch(index)
            {
                case 0: return dScale;
                case 4: return (clipSizeStruct.x / clipSizeStruct.y) * dScale;
                case 8: return 1f;
                default: return 0f;
            }
        };
    }

    @Override
    public Matrix3fElements elements()
    {
        clipSizeStruct.apply(clipSize);
        dScale = 1f / worldScale.read();
        if (clipSizeStruct.x > clipSizeStruct.y)
        {
            return elementsW;
        }
        else
        {
            return elementsH;
        }
    }
}