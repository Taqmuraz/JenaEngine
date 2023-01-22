package jena.engine.math;

public class Matrix3fRect implements Matrix3f
{
    Rectf rect;

    public Matrix3fRect(Rectf rect)
    {
        this.rect = rect;
    }

    @Override
    public Matrix3fElements elements()
    {
        RectfStruct r = new RectfStruct(rect);
        return i ->
        {
            switch(i)
            {
                case 0: return r.width;
                case 4: return r.height;
                default:
                case 1:
                case 2:
                case 3:
                case 5: return 0f;
                case 6: return r.x;
                case 7: return r.y;
                case 8: return 1f;
            }
        };
    }
}