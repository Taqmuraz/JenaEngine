package jena.swing;

import java.awt.Graphics2D;
import java.awt.Rectangle;

import jena.engine.graphics.GraphicsClipPainter;
import jena.engine.graphics.GraphicsDevice;
import jena.engine.math.Rectf;

public class SwingGraphicsDevice implements GraphicsDevice
{
    Graphics2D graphics;

    public SwingGraphicsDevice(Graphics2D graphics)
    {
        this.graphics = graphics;
    }

    @Override
    public void paintRect(Rectf rect, GraphicsClipPainter paint)
    {
        rect.accept((x, y, w, h) ->
        {
            SwingGraphicsClip clip = new SwingGraphicsClip(graphics, new Rectangle.Float(x, y, w, h));
            paint.paint(clip);
        });
    }
}