package jena.engine.math;

public class Matrix3fRotation implements Matrix3f
{
    ValueFloat angle;
    private class Elements implements Matrix3fElements
    {
        float angle;
        float sin;
        float cos;

        public void update()
        {
            angle = Matrix3fRotation.this.angle.read();
            sin = (float)Math.sin(angle);
            cos = (float)Math.cos(angle);
        }

        @Override
        public float at(int index)
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
        }
    }
    Elements elements;


    public Matrix3fRotation(ValueFloat angle)
    {
        this.angle = angle;
        elements = new Elements();
    }

    @Override
    public Matrix3fElements elements()
    {
        elements.update();
        return elements;
    }
}