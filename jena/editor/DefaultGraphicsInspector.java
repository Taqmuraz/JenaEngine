package jena.editor;

import jena.engine.graphics.Color;
import jena.engine.graphics.GraphicsClip;
import jena.engine.graphics.GraphicsClipPainter;
import jena.engine.math.ValueFloat;
import jena.engine.math.Vector2f;

public class DefaultGraphicsInspector implements GraphicsInspector, GraphicsClipPainter
{
    GraphicsClipPainter painter;

    public DefaultGraphicsInspector(GraphicsInspectable root)
    {
        painter = root.inspect(this);
    }

    @Override
    public void paint(GraphicsClip clip)
    {
        painter.paint(clip);
    }

    @Override
    public PointHandle pointHandle(Vector2f position, Color color, ValueFloat radius)
    {
        return new PointHandle()
        {
            public Vector2f position()
            {
                return position;
            }
            public void paint(GraphicsClip clip)
            {
                position.accept((x, y) -> clip.drawEllipse(a ->
                {
                    float r = radius.read();
                    a.call(x - r, y - r, r * 2f, r * 2f);
                }, color, () -> 0.2f));
            }
        };
    }
}