package jena.engine.math;

import java.nio.FloatBuffer;
import java.util.stream.Stream;

public class MultipleFieldVector2f implements FieldVector2f
{
    FieldVector2f[] fields;

    public MultipleFieldVector2f(FieldVector2f... fields)
    {
        this.fields = fields;
    }

    @Override
    public Vector2f project(Vector2f source)
    {
        return acceptor ->
        {
            FloatBuffer min = FloatBuffer.allocate(2);
            Stream.of(fields).map(f -> f.project(source))
            .min((a, b) ->
            {
                new Vector2fLength(new Vector2fSub(a, source)).accept(f -> min.put(0, f));
                new Vector2fLength(new Vector2fSub(b, source)).accept(f -> min.put(1, f));
                return Float.compare(min.get(0), min.get(1));
            })
            .ifPresent(v -> v.accept(acceptor));
        };
    }
}