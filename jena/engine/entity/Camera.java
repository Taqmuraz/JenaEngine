package jena.engine.entity;

import jena.engine.graphics.Color;
import jena.engine.graphics.GraphicsClipPainter;
import jena.engine.graphics.GraphicsDevice;
import jena.engine.graphics.GraphicsDevicePainter;
import jena.engine.math.Matrix3fOrtho;
import jena.engine.math.Matrix3fViewport;
import jena.engine.math.Rectf;
import jena.engine.math.Vector2f;
import jena.engine.math.Vector2fNegative;
import jena.engine.math.Matrix3fBuilder;

public class Camera implements GraphicsDevicePainter
{
    private GraphicsClipPainter scene;
    private Color clearColor;
    private Rectf clip;
    private Vector2f position;

    public Camera(Rectf clip, Color clearColor, Vector2f position, GraphicsClipPainter scene)
    {
        this.clearColor = clearColor;
        this.scene = scene;
        this.clip = clip;
        this.position = position;
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
            graphicsClip.matrixScope(
                source -> r -> clip.accept((x, y, w, h) -> new Matrix3fBuilder(source)
                    .translate(a -> a.call(x, y))
                    .multiply(new Matrix3fViewport(w, h))
                    .multiply(new Matrix3fOrtho(a -> a.call(w, h), a -> a.call(5f)))
                    .translate(new Vector2fNegative(position))
                    .build().accept(r)),
                () -> scene.paint(graphicsClip));
        });
    }
}