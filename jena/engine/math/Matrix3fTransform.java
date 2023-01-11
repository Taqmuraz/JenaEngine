package jena.engine.math;

public class Matrix3fTransform extends Matrix3fRotation
{
    Vector2f position;
    Vector2f scale;
    Vector2fStruct positionStruct = new Vector2fStruct();
    Vector2fStruct scaleStruct = new Vector2fStruct();

    public Matrix3fTransform(Vector2f position, ValueFloat rotation)
    {
        super(rotation);
        this.position = position;
        this.scale = a -> a.call(1f, 1f);
    }
    public Matrix3fTransform(Vector2f position, Vector2f scale, ValueFloat rotation)
    {
        super(rotation);
        this.position = position;
        this.scale = scale;
    }

    @Override
    public Matrix3fElements elements()
    {
        Matrix3fElements base = super.elements();
        positionStruct.apply(position);
        scaleStruct.apply(scale);
        return index ->
        {
            switch(index)
            {
                case 0: return base.at(0) * scaleStruct.x;
                case 1: return base.at(1) * scaleStruct.x;
                case 3: return base.at(3) * scaleStruct.y;
                case 4: return base.at(4) * scaleStruct.y;
                case 6: return positionStruct.x;
                case 7: return positionStruct.y;
                case 8: return 1f;
                default: return 0f;
            }
        };
    }
}