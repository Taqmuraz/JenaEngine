package jena.editor;

import jena.engine.graphics.Color;
import jena.engine.graphics.GraphicsBrush;
import jena.engine.graphics.GraphicsBrushPainter;
import jena.engine.graphics.GraphicsDrawingPainter;
import jena.engine.graphics.GraphicsPainter;
import jena.engine.graphics.GraphicsState;
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
import jena.engine.graphics.MatrixScopeGraphicsPainter;

public class ClipGraphicsInspector implements GraphicsInspector, GraphicsBrushPainter, GraphicsState
{
    private GraphicsInspectable root;
    private Matrix3fStack matrixStack;
    private Mouse mouse;
    private FieldVector2f mouseToScene;
    private GraphicsBrush brush;

    public ClipGraphicsInspector(GraphicsBrush brush, GraphicsInspectable root, Mouse mouse, FieldVector2f mouseToScene)
    {
        matrixStack = new Matrix3fStack();
        this.mouse = mouse;
        this.mouseToScene = mouseToScene;
        this.brush = brush;
    }

    @Override
    public void matrixScope(Transformation transformation, GraphicsPainter painter)
    {
        matrixStack.matrixScope(transformation, () ->
        {
            painter.paint(this);
        });
    }

    @Override
    public GraphicsPainter paint(GraphicsBrush brush)
    {
        return root.inspect(this);
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
        GraphicsPainter painter = new MatrixScopeGraphicsPainter(
            transformation,
            new GraphicsDrawingPainter(
                brush.drawEllipse(graphicsRect, color, a -> a.call(0.1f))));

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
            public void paint(GraphicsState state)
            {
                painter.paint(state);
            }
        };
    }
}