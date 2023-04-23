package jena.swing;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;

import jena.engine.graphics.GraphicsBrushPainter;
import jena.engine.graphics.GraphicsDevice;
import jena.engine.graphics.GraphicsDrawing;
import jena.engine.math.Rectf;

public class SwingGraphicsDevice implements GraphicsDevice
{
    SwingGraphics graphics;
    SwingTextureResource textureResource;

    public SwingGraphicsDevice(SwingGraphics graphics, SwingTextureResource textureResource)
    {
        this.graphics = graphics;
        this.textureResource = textureResource;
    }

    @Override
    public GraphicsDrawing paintRect(Rectf rect, GraphicsBrushPainter paint)
    {
        SwingGraphicsClip clip = new SwingGraphicsClip(graphics, textureResource);
        GraphicsDrawing drawing = paint.paint(clip).paint(clip);
        return () ->
        {
            Graphics2D graphics = this.graphics.graphics();
            rect.accept((x, y, w, h) -> graphics.setClip(new Rectangle.Float(x, y, w, h)));
            graphics.setTransform(new AffineTransform());
            drawing.draw();
        };
    }
}