package jena.engine.math;

public class MultiplePunctureFieldVector2f implements FieldVector2f
{
    FieldVector2f[] fields;

    public MultiplePunctureFieldVector2f(FieldVector2f... fields)
    {
        this.fields = fields;
    }

    @Override
    public Vector2f project(Vector2f source)
    {
        Vector2f proj = source;
        for (FieldVector2f field : fields) proj = field.project(proj);
        return proj;
    }
}