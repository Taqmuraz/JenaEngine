package jena.engine.entity;

import jena.engine.graphics.Color;
import jena.engine.graphics.GraphicsClipPainter;
import jena.engine.graphics.GraphicsDevice;
import jena.engine.graphics.GraphicsDevicePainter;
import jena.engine.math.Matrix3fOrtho;
import jena.engine.math.Matrix3fTranslation;
import jena.engine.math.Matrix3fViewport;
import jena.engine.math.Rectf;
import jena.engine.math.ValueFloat;
import jena.engine.math.Vector2f;
import jena.engine.math.Vector2fNegative;
import jena.engine.math.Vector2fTransformPoint;
import jena.engine.math.FieldVector2f;
import jena.engine.math.Matrix3f;
import jena.engine.math.Matrix3fMul;

public class Camera implements GraphicsDevicePainter
{
    private GraphicsClipPainter scene;
    private Color clearColor;
    private Rectf clip;
    private Matrix3f w2s;
    private Matrix3f s2w;

    public Camera(Rectf clip, Color clearColor, Vector2f position, GraphicsClipPainter scene)
    {
        this.clearColor = clearColor;
        this.scene = scene;
        this.clip = clip;

        ValueFloat orthoSize = a -> a.call(5f);
        
        w2s = r -> clip.accept((x, y, w, h) ->
            new Matrix3fTranslation(a -> a.call(x, y))
            .multiply(new Matrix3fViewport(w, h))
            .multiply(new Matrix3fOrtho(a -> a.call(w, h), orthoSize))
            .translate(new Vector2fNegative(position))
            .accept(r));

        s2w = w2s.inverse();
    }

    public Camera(Rectf clip, Color clearColor, GraphicsClipPainter scene)
    {
        this(clip, clearColor, a -> a.call(0f, 0f), scene);
    }

    @Override
    public void paint(GraphicsDevice graphicsDevice)
    {
        graphicsDevice.paintRect(clip, graphicsClip ->
        {
            graphicsClip.fillRect(clip, clearColor);
            graphicsClip.matrixScope(source -> new Matrix3fMul(source, w2s), () -> scene.paint(graphicsClip));
        });
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