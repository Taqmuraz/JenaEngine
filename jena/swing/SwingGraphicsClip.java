package jena.swing;

import java.awt.Graphics2D;

import jena.engine.graphics.Color;
import jena.engine.graphics.GraphicsBrush;
import jena.engine.graphics.GraphicsDrawing;
import jena.engine.graphics.GraphicsPainter;
import jena.engine.graphics.GraphicsState;
import jena.engine.graphics.Text;
import jena.engine.graphics.TextureHandle;
import jena.engine.graphics.Transformation;
import jena.engine.math.Matrix3fPipeline;
import jena.engine.math.Matrix3fStack;
import jena.engine.math.Rectf;
import jena.engine.math.ValueFloat;
import jena.engine.math.Vector2f;
import jena.engine.math.Vector2fNormalized;

import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.util.stream.IntStream;

public class SwingGraphicsClip implements GraphicsBrush, GraphicsState
{
    private SwingGraphics graphics;
    private AffineTransform transform;
    private AffineTransform identity;
    private AffineTransform line;
    private AffineTransform buffer;
    private Matrix3fPipeline matrixStack;
    private SwingTextureResource textureResource;

    public SwingGraphicsClip(SwingGraphics graphics, SwingTextureResource textureResource)
    {
        this.graphics = graphics;
        this.textureResource = textureResource;
        line = new AffineTransform();
        buffer = new AffineTransform();
        identity = new AffineTransform();
        transform = new AffineTransform();
        matrixStack = new Matrix3fStack();
    }

    @Override
    public GraphicsDrawing drawSprite(TextureHandle texture, Rectf source, Rectf destination)
    {
        return () -> texture.bind(() ->
        {
            Graphics2D graphics = this.graphics.graphics();
            textureResource.accept(descriptor -> descriptor.acceptImage(image ->
            {
                destination.accept((dx, dy, dw, dh) ->
                    descriptor.acceptSize((iw, ih) ->
                    {
                        source.accept((sx, sy, sw, sh) -> 
                        {
                            graphics.translate(dx, dy);
                            graphics.scale(dw, dh);
                            graphics.drawImage(image,
                                    0, 1, 1, 0,
                                    (int)(sx * iw), (int)(sy * ih), (int)((sx + sw) * iw), (int)((sy + sh) * ih),
                                    null);
                            /* debug
                            graphics.setStroke(new java.awt.BasicStroke(0.01f));
                            graphics.setColor(java.awt.Color.red);
                            graphics.drawLine(0, 0, 1, 1);
                            graphics.drawLine(1, 0, 0, 1);
                            */
                            graphics.setTransform(transform);
                        });
                    }));
            }));
        });
    }

    @Override
    public GraphicsDrawing drawTile(TextureHandle texture, Vector2f tiles, Rectf destination)
    {
        return () -> tiles.accept((x, y) -> destination.accept((dx, dy, dw, dh) ->
        {
            int ix = (int)x;
            int iy = (int)y;
            float tw = dw / ix;
            float th = dh / iy;
            IntStream.range(0, ix * iy).forEach(i -> drawSprite(texture, a -> a.call(0f, 0f, 1f, 1f), a -> a.call(dx + (i % ix) * tw, dy + (i / ix) * th, tw, th)).draw());
        }));
    }

    @Override
    public GraphicsDrawing fillRect(Rectf rect, jena.engine.graphics.Color color)
    {
        Point2D.Float
            srcA = new Point2D.Float(),
            srcB = new Point2D.Float(),
            dstA = new Point2D.Float(),
            dstB = new Point2D.Float();
        
        return () -> rect.accept((x, y, w, h) ->
            color.accept((cr, cg, cb, ca) ->
            {
                Graphics2D graphics = this.graphics.graphics();
                graphics.setTransform(identity);
        
                srcA.setLocation(x, y);
                srcB.setLocation(x + w, y + h);

                transform.transform(srcA, dstA);
                transform.transform(srcB, dstB);
                int minX = (int)Math.min(dstA.x, dstB.x);
                int minY = (int)Math.min(dstA.y, dstB.y);
                int width = (int)(Math.abs(dstA.x - dstB.x));
                int height = (int)(Math.abs(dstA.y - dstB.y));
                
                graphics.setBackground(new java.awt.Color(cr, cg, cb, ca));
                graphics.clearRect(minX, minY, width, height);

                graphics.setTransform(transform);
            }));
    }

    @Override
    public GraphicsDrawing drawText(Text text, Rectf rect, Color color)
    {
        return () -> rect.accept((x, y, w, h) -> text.accept(content ->
        {
            Graphics2D graphics = this.graphics.graphics();
            java.awt.FontMetrics metrics = graphics.getFontMetrics(graphics.getFont());
            float bw = metrics.stringWidth(content);
            float bh = metrics.getLineMetrics(content, graphics).getHeight();

            float bd = bw / bh;
            float d = w / h;
            float sx, sy, sw, sh;

            if (bd > d)
            {
                sx = 0f;
                sh = (bh / bw) * w;
                sy = (h - sh) * 0.5f;
                sw = w;
            }
            else
            {
                sy = 0f;
                sh = h;
                sw = (bw / bh) * h;
                sx = (w - sw) * 0.5f;
            }

            color.accept((cr, cg, cb, ca) -> graphics.setColor(new java.awt.Color(cr, cg, cb, ca)));
            graphics.translate(x + sx, y + sy + sh);
            graphics.scale(sw / bw, sh / bh);
            graphics.drawString(content, 0, 0);
            graphics.setTransform(transform);
        }));
    }

    @Override
    public void matrixScope(Transformation transformation, GraphicsPainter painter)
    {
        matrixStack.matrixScope(transformation, () ->
        {
            updateTransform(graphics.graphics());
            painter.paint(this);
        });
        updateTransform(graphics.graphics());
    }

    private void updateTransform(Graphics2D graphics)
    {
        matrixStack.accept(e ->
        {
            transform.setTransform(e.at(0), e.at(1), e.at(3), e.at(4), e.at(6), e.at(7));
            graphics.setTransform(transform);
        });
    }

    @Override
    public GraphicsDrawing drawLine(Vector2f a, Vector2f b, Color color, ValueFloat width)
    {
        return () -> a.accept((ax, ay) -> b.accept((bx, by) -> width.accept(w ->
        {
            Graphics2D graphics = this.graphics.graphics();
            color.accept((cr, cg, cb, ca) -> graphics.setColor(new java.awt.Color(cr, cg, cb, ca)));
            graphics.setStroke(new java.awt.BasicStroke(w));
            line.setTransform(transform);
            new Vector2fNormalized(v -> v.call(bx - ax, by - ay)).accept((dx, dy) ->
                buffer.setTransform(bx - ax, by - ay, -dy, dx, ax, ay));
            line.concatenate(buffer);
            graphics.setTransform(line);
            graphics.drawLine(0, 0, 1, 0);
            graphics.setTransform(transform);
        })));
    }
    @Override
    public GraphicsDrawing drawEllipse(Rectf rect, Color color, ValueFloat width)
    {
        return () -> rect.accept((x, y, w, h) -> width.accept(strokeWidth ->
        {
            Graphics2D graphics = this.graphics.graphics();
            color.accept((cr, cg, cb, ca) -> graphics.setColor(new java.awt.Color(cr, cg, cb, ca)));
            graphics.translate(x, y);
            graphics.scale(w, h);
            graphics.setStroke(new java.awt.BasicStroke(strokeWidth));
            graphics.drawArc(0, 0, 1, 1, 0, 360);
            graphics.setTransform(transform);
        }));
    }
    @Override
    public GraphicsDrawing drawRect(Rectf rect, Color color, ValueFloat width)
    {
        return () -> rect.accept((x, y, w, h) -> width.accept(strokeWidth ->
        {
            Graphics2D graphics = this.graphics.graphics();
            color.accept((cr, cg, cb, ca) -> graphics.setColor(new java.awt.Color(cr, cg, cb, ca)));
            graphics.setStroke(new java.awt.BasicStroke(strokeWidth / ((w + h) * 0.5f)));
            graphics.translate(x, y);
            graphics.scale(w, h);
            graphics.drawRect(0, 0, 1, 1);
            graphics.setTransform(transform);
        }));
    }
    @Override
    public GraphicsDrawing fillEllipse(Rectf rect, Color color)
    {
        return () -> rect.accept((x, y, w, h) ->
        {
            Graphics2D graphics = this.graphics.graphics();
            color.accept((cr, cg, cb, ca) -> graphics.setColor(new java.awt.Color(cr, cg, cb, ca)));
            graphics.translate(x, y);
            graphics.scale(w, h);
            graphics.fillArc(0, 0, 1, 1, 0, 360);
            graphics.setTransform(transform);
        });
    }
}
