package jena.engine.math;

import java.util.stream.Stream;

public class RectPunctureFieldVector2f implements FieldVector2f
{
    Rectf rect;

    public RectPunctureFieldVector2f(Rectf rect)
    {
        this.rect = rect;
    }

    @Override
    public Vector2f project(Vector2f source)
    {
        return acceptor ->
        {
            RectfStruct s = new RectfStruct(rect);
            if (s.contains(source))
            {
                source.accept((x, y) -> Stream.<Vector2f>of(
                    a -> a.call(s.x, y),
                    a -> a.call(s.x + s.width, y),
                    a -> a.call(x, s.y),
                    a -> a.call(x, s.y + s.height)
                ).min((a, b) -> a.sub(source).compareLength(b.sub(source)))
                .ifPresentOrElse(v -> v.accept(acceptor), () -> source.accept(acceptor)));
            }
            else
            {
                source.accept(acceptor);
            }
        };
    }
}