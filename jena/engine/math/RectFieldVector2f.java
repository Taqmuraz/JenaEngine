package jena.engine.math;

public class RectFieldVector2f implements FieldVector2f
{
    Rectf rect;

    public RectFieldVector2f(Rectf rect)
    {
        this.rect = rect;
    }

    @Override
    public Vector2f project(Vector2f source)
    {
        return new Vector2fClampRect(source, rect);
    }
}