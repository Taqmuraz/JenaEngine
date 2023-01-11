package jena.engine.entity;

import jena.engine.graphics.Color;
import jena.engine.graphics.GraphicsClipPainter;
import jena.engine.graphics.GraphicsDevice;
import jena.engine.graphics.GraphicsDevicePainter;
import jena.engine.math.Matrix3fOrtho;
import jena.engine.math.Matrix3fViewport;
import jena.engine.math.Rectf;
import jena.engine.math.Matrix3fBuilder;

public class Camera implements GraphicsDevicePainter
{
    private GraphicsClipPainter scene;
    private Color clearColor;
    private Rectf clip;

    public Camera(Rectf clip, Color clearColor, GraphicsClipPainter scene)
    {
        this.clearColor = clearColor;
        this.scene = scene;
        this.clip = clip;
    }

    @Override
    public void paint(GraphicsDevice graphicsDevice)
    {
        graphicsDevice.paintRect(clip, graphicsClip ->
        {
            graphicsClip.fillRect(clip, clearColor);
            clip.accept((x, y, w, h) -> graphicsClip.matrixScope(
                source -> new Matrix3fBuilder(source)
                    .translate(a -> a.call(x, y))
                    .multiply(new Matrix3fViewport(w, h))
                    .multiply(new Matrix3fOrtho(a -> a.call(w, h), () -> 1f))
                    .build(),
                () -> scene.paint(graphicsClip)));
        });
    }
}