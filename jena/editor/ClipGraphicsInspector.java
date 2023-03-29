package jena.editor;

import jena.engine.common.ActionBox;
import jena.engine.common.Function;
import jena.engine.graphics.Color;
import jena.engine.graphics.GraphicsClip;
import jena.engine.graphics.GraphicsClipPainter;
import jena.engine.graphics.MultiplicationTransformation;
import jena.engine.graphics.Transformation;
import jena.engine.input.Key;
import jena.engine.input.Mouse;
import jena.engine.math.CircleContainsPoint;
import jena.engine.math.FieldVector2f;
import jena.engine.math.Matrix3f;
import jena.engine.math.Matrix3fStack;
import jena.engine.math.Rectf;
import jena.engine.math.RectfLocationSize;
import jena.engine.math.ValueBoolean;
import jena.engine.math.ValueFloat;
import jena.engine.math.Vector2f;
import jena.engine.math.Vector2fStruct;
import jena.engine.math.Vector2fValueFloat;

public class ClipGraphicsInspector implements GraphicsInspector, GraphicsClipPainter
{
    private GraphicsClipPainter painter;
    private Matrix3fStack matrixStack;
    private Mouse mouse;
    private FieldVector2f mouseToScene;

    public ClipGraphicsInspector(GraphicsInspectable root, Mouse mouse, FieldVector2f mouseToScene)
    {
        matrixStack = new Matrix3fStack();
        this.mouse = mouse;
        this.mouseToScene = mouseToScene;
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
        Vector2fStruct initial = new Vector2fStruct(position);
        Vector2f mousePosition = mouseToScene.project(mouse.position());
        ValueBoolean mouseInside = new CircleContainsPoint(initial, radius, mousePosition);
        Key mouseLeft = mouse.button(0);
        Vector2f radiusBox = new Vector2fValueFloat(radius, radius);
        Vector2f graphicsRectLocation = initial.sub(radiusBox);
        Vector2f graphicsRectSize = radiusBox.mul(a -> a.call(2f));
        Rectf graphicsRect = new RectfLocationSize(graphicsRectLocation, graphicsRectSize);
        Matrix3f transform = matrixStack.peek();
        Transformation transformation = new MultiplicationTransformation(transform);
        
        return new PointHandle()
        {
            public Vector2f position()
            {
                return a ->
                {
                    mouseInside.accept(inside ->
                    {
                        if(mouseLeft.isHold() && inside)
                        {
                            mousePosition.accept(initial::apply);
                        }
                    });
                    a.call(initial.x, initial.y);
                };
            }
            public void paint(GraphicsClip clip)
            {
                clip.matrixScope(transformation, () ->
                {
                    clip.drawEllipse(graphicsRect, color, a -> a.call(0.1f));
                });
            }
        };
    }
}