package jena.engine.math;

public final class CircleContainsPoint implements ValueBoolean
{
    private ValueFloat delta;
    private ValueFloat radius;
    
    public CircleContainsPoint(Vector2f center, ValueFloat radius, Vector2f point)
    {
        this.radius = radius;
        this.delta = new Vector2fLength(point.sub(center));
    }

    @Override
    public void accept(BooleanAcceptor acceptor)
    {
        delta.accept(d -> radius.accept(r ->
        {
            acceptor.call(d <= r);
        }));
    }
}