package jena.game;

import jena.engine.graphics.Color;
import jena.engine.graphics.GraphicsBrushPainter;
import jena.engine.graphics.GraphicsDevice;
import jena.engine.graphics.GraphicsDevicePainter;
import jena.engine.graphics.GraphicsDrawing;
import jena.engine.graphics.GraphicsDrawingPainter;
import jena.engine.graphics.MatrixScopeGraphicsPainter;
import jena.engine.math.Matrix3fOrtho;
import jena.engine.math.Matrix3fTranslation;
import jena.engine.math.Matrix3fViewport;
import jena.engine.math.Rectf;
import jena.engine.math.ValueFloat;
import jena.engine.math.Vector2f;
import jena.engine.math.Vector2fNegative;
import jena.engine.math.Vector2fRectLocation;
import jena.engine.math.Vector2fRectSize;
import jena.engine.math.Vector2fTransformPoint;
import jena.engine.math.FieldVector2f;
import jena.engine.math.Matrix3f;
import jena.engine.graphics.CompositeGraphicsPainter;

public class Camera implements GraphicsDevicePainter
{
    private GraphicsBrushPainter scene;
    private Color clearColor;
    private Rectf clip;
    private Matrix3f w2s;
    private Matrix3f s2w;

    public Camera(Rectf clip, Color clearColor, Vector2f position, GraphicsBrushPainter scene)
    {
        this.clearColor = clearColor;
        this.scene = scene;
        this.clip = clip;

        ValueFloat orthoSize = a -> a.call(5f);
        Vector2f clipSize = new Vector2fRectSize(clip);
        
        w2s = new Matrix3fTranslation(new Vector2fRectLocation(clip))
            .multiply(new Matrix3fViewport(clipSize))
            .multiply(new Matrix3fOrtho(clipSize, orthoSize))
            .translate(new Vector2fNegative(position));

        s2w = w2s.inverse();
    }

    public Camera(Rectf clip, Color clearColor, GraphicsBrushPainter scene)
    {
        this(clip, clearColor, a -> a.call(0f, 0f), scene);
    }

    @Override
    public GraphicsDrawing paint(GraphicsDevice graphicsDevice)
    {
        return graphicsDevice.paintRect(clip, graphicsClip ->
        new CompositeGraphicsPainter(
            new GraphicsDrawingPainter(graphicsClip.fillRect(clip, clearColor)),
            new MatrixScopeGraphicsPainter(w2s, scene.paint(graphicsClip))
        ));
    }

    public FieldVector2f worldToScreen()
    {
        return point -> new Vector2fTransformPoint(w2s, point);
    }
    public FieldVector2f screenToWorld()
    {
        return point -> new Vector2fTransformPoint(s2w, point);
    }
}