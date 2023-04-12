package jena.swing;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import jena.engine.graphics.GraphicsBrushPainter;
import jena.engine.graphics.GraphicsDevice;
import jena.engine.graphics.GraphicsDrawing;
import jena.engine.graphics.GraphicsPainter;
import jena.engine.math.Rectf;

public class SwingGraphicsDevice implements GraphicsDevice
{
    Graphics2D graphics;
    SwingTextureResource textureResource;

    public SwingGraphicsDevice(Graphics2D graphics, SwingTextureResource textureResource)
    {
        this.graphics = graphics;
        this.textureResource = textureResource;
    }

    class RectShape implements Shape
    {
        Rectangle.Float rect;
        public RectShape(Rectf rect)
        {
            rect.accept((x, y, w, h) -> this.rect = new Rectangle.Float(x, y, w, h));
        }
        @Override
        public boolean contains(Point2D p)
        {
            return rect.contains(p);
        }
        @Override
        public boolean contains(Rectangle2D r)
        {
            return rect.contains(r);
        }
        @Override
        public boolean contains(double x, double y)
        {
            return rect.contains(x, y);
        }
        @Override
        public boolean contains(double x, double y, double w, double h)
        {
            return rect.contains(x, y, w, h);
        }
        @Override
        public Rectangle getBounds()
        {
            return rect.getBounds();
        }
        @Override
        public Rectangle2D getBounds2D()
        {
            return rect.getBounds2D();
        }
        @Override
        public PathIterator getPathIterator(AffineTransform at)
        {
            return rect.getPathIterator(at);
        }
        @Override
        public PathIterator getPathIterator(AffineTransform at, double flatness)
        {
            return rect.getPathIterator(at, flatness);
        }
        @Override
        public boolean intersects(Rectangle2D r)
        {
            return rect.intersects(r);
        }
        @Override
        public boolean intersects(double x, double y, double w, double h)
        {
            return rect.intersects(x, y, w, h);
        }
    }

    @Override
    public GraphicsDrawing paintRect(Rectf rect, GraphicsBrushPainter paint)
    {
        SwingGraphicsClip clip = new SwingGraphicsClip(graphics, new RectShape(rect), textureResource);
        GraphicsPainter result = paint.paint(clip);
        return () -> result.paint(clip);
    }
}