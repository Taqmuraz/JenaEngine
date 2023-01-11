package jena.engine.math;

public class Matrix3fRotation implements Matrix3f
{
    ValueFloat angle;
    public Matrix3fRotation(ValueFloat angle)
    {
        this.angle = angle;
    }

    @Override
    public Matrix3fElements elements()
    {
        float angle = this.angle.read();
        float sin = (float)Math.sin(angle);
        float cos = (float)Math.cos(angle);
        return index ->
        {
            switch(index)
            {
                case 0:return cos;
                case 1:return sin;
                case 3:return -sin;
                case 4:return cos;
                case 8:return 1f;
                default:return 0f;
            }
        };
    }
}