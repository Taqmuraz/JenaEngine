package jena.engine.math;

import java.util.stream.Stream;

public class MultipleClampFieldVector2f implements FieldVector2f
{
    FieldVector2f[] fields;

    public MultipleClampFieldVector2f(FieldVector2f... fields)
    {
        this.fields = fields;
    }

    @Override
    public Vector2f project(Vector2f source)
    {
        return acceptor ->
        {
            Stream.of(fields).map(f -> f.project(source))
            .min((a, b) -> a.sub(source).compareLength(b.sub(source)))
            .ifPresentOrElse(v -> v.accept(acceptor), () -> source.accept(acceptor));
        };
    }
}