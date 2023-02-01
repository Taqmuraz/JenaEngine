package jena.editor;

import jena.engine.common.ActionBox;
import jena.engine.common.Function;
import jena.engine.graphics.Color;
import jena.engine.graphics.GraphicsClip;
import jena.engine.graphics.GraphicsClipPainter;
import jena.engine.graphics.Transformation;
import jena.engine.math.Matrix3fPipeline;
import jena.engine.math.Matrix3fStack;
import jena.engine.math.ValueFloat;
import jena.engine.math.Vector2f;

public class ClipGraphicsInspector implements GraphicsInspector, GraphicsClipPainter
{
    GraphicsClipPainter painter;
    Matrix3fPipeline matrixStack;

    public ClipGraphicsInspector(GraphicsInspectable root)
    {
        matrixStack = new Matrix3fStack();
        painter = root.inspect(this);
    }

    @Override
    public GraphicsClipPainter matrixScope(Transformation transformation, Function<GraphicsClipPainter> function)
    {
        ActionBox<GraphicsClipPainter> box = new ActionBox<GraphicsClipPainter>(function);
        matrixStack.matrixScope(transformation, box);
        return box.result();
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
                position.accept((x, y) -> radius.accept(r -> clip.drawEllipse(a ->
                {
                    a.call(x - r, y - r, r * 2f, r * 2f);
                }, color, a -> a.call(0.2f))));
            }
        };
    }
}