package jena.engine.math;

public class Matrix3fTransform extends Matrix3fRotation
{
    Vector2f position;
    Vector2f scale;

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
        Vector2fStruct position = new Vector2fStruct(this.position);
        Vector2fStruct scale = new Vector2fStruct(this.scale);
        return index ->
        {
            switch(index)
            {
                case 0: return base.at(0) * scale.x;
                case 1: return base.at(1) * scale.x;
                case 3: return base.at(3) * scale.y;
                case 4: return base.at(4) * scale.y;
                case 6: return position.x;
                case 7: return position.y;
                case 8: return 1f;
                default: return 0f;
            }
        };
    }
}