package jena.swing;

import java.awt.Graphics2D;

import jena.engine.common.Action;
import jena.engine.graphics.Color;
import jena.engine.graphics.GraphicsClip;
import jena.engine.graphics.Text;
import jena.engine.graphics.TextureHandle;
import jena.engine.graphics.Transformation;
import jena.engine.math.Matrix3fElements;
import jena.engine.math.Matrix3fPipeline;
import jena.engine.math.Matrix3fStack;
import jena.engine.math.Rectf;
import jena.engine.math.ValueFloat;
import jena.engine.math.Vector2f;
import jena.engine.math.Vector2fNormalized;
import jena.engine.math.Vector2fStruct;

import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.Shape;

public class SwingGraphicsClip implements GraphicsClip
{
    private Graphics2D graphics;
    private AffineTransform transform;
    private AffineTransform identity;
    private AffineTransform line;
    private AffineTransform buffer;
    private Shape clip;
    private Matrix3fPipeline matrixStack;

    public SwingGraphicsClip(Graphics2D graphics)
    {
        this(graphics, graphics.getClip());
    }
    public SwingGraphicsClip(Graphics2D graphics, Shape clip)
    {
        this.graphics = graphics;
        this.clip = clip;
        line = new AffineTransform();
        buffer = new AffineTransform();
        identity = new AffineTransform();
        transform = new AffineTransform();
        matrixStack = new Matrix3fStack();
        reset();
    }

    @Override
    public void drawSprite(TextureHandle texture, Rectf source, Rectf destination)
    {
        if (texture instanceof SwingTextureResource)
        {
            SwingTextureResource swingTexture = (SwingTextureResource)texture;
            swingTexture.accept(descriptor -> descriptor.acceptImage(image ->
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
        }
    }

    @Override
    public void drawTile(TextureHandle texture, Vector2f tiles, Rectf destination)
    {
        if (texture instanceof SwingTextureResource)
        {
            SwingTextureResource swingTexture = (SwingTextureResource)texture;
            swingTexture.accept(descriptor -> descriptor.acceptPaint(paint ->
            {
                destination.accept((dx, dy, dw, dh) ->
                {
                    tiles.accept((sw, sh) ->
                    {
                        graphics.setPaint(paint);
                        float scaleX = sw == 0f ? 0f : 1f / sw;
                        float scaleY = sh == 0f ? 0f : 1f / sh;
                        graphics.translate(dx, dy + dh);
                        graphics.scale(scaleX * dw, -scaleY * dh);
                        graphics.fill(new java.awt.geom.Rectangle2D.Float(0f, 0f, sw, sh));
                        graphics.setTransform(transform);
                    });
                });
            }));
        }
    }

    @Override
    public void fillRect(Rectf rect, jena.engine.graphics.Color color)
    {
        rect.accept((x, y, w, h) ->
            color.accept((cr, cg, cb, ca) ->
            {
                graphics.setTransform(identity);
        
                Point2D.Float srcA = new Point2D.Float(x, y);
                Point2D.Float srcB = new Point2D.Float(x + w, y + h);
                Point2D.Float dstA = new Point2D.Float();
                Point2D.Float dstB = new Point2D.Float();

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
    public void drawText(Text text, Rectf rect, Color color)
    {
        rect.accept((x, y, w, h) ->
        {
            String content = text.content();
            var metrics = graphics.getFontMetrics(graphics.getFont());
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
        });
    }

    @Override
    public void matrixScope(Transformation transformation, Action action)
    {
        matrixStack.matrixScope(transformation, () ->
        {
            updateTransform();
            action.call();
        });
        updateTransform();
    }

    private void updateTransform()
    {
        Matrix3fElements e = matrixStack.elements();
        transform.setTransform(e.at(0), e.at(1), e.at(3), e.at(4), e.at(6), e.at(7));
        graphics.setTransform(transform);
    }

    private void reset()
    {
        graphics.setClip(clip);
        transform.setToIdentity();
        graphics.setTransform(transform);
    }

    @Override
    public void drawLine(Vector2f a, Vector2f b, Color color, ValueFloat width)
    {
        a.accept((ax, ay) -> b.accept((bx, by) ->
        {
            color.accept((cr, cg, cb, ca) -> graphics.setColor(new java.awt.Color(cr, cg, cb, ca)));
            graphics.setStroke(new java.awt.BasicStroke(width.read()));
            Vector2fStruct d = new Vector2fStruct(new Vector2fNormalized(v -> v.call(bx - ax, by - ay)));
            line.setTransform(transform);
            buffer.setTransform(bx - ax, by - ay, -d.y, d.x, ax, ay);
            line.concatenate(buffer);
            graphics.setTransform(line);
            graphics.drawLine(0, 0, 1, 0);
            graphics.setTransform(transform);
        }));
    }
    @Override
    public void drawEllipse(Rectf rect, Color color, ValueFloat width)
    {
        rect.accept((x, y, w, h) ->
        {
            color.accept((cr, cg, cb, ca) -> graphics.setColor(new java.awt.Color(cr, cg, cb, ca)));
            graphics.translate(x, y);
            graphics.scale(w, h);
            graphics.setStroke(new java.awt.BasicStroke(width.read()));
            graphics.drawArc(0, 0, 1, 1, 0, 360);
            graphics.setTransform(transform);
        });
    }
    @Override
    public void drawRect(Rectf rect, Color color, ValueFloat width)
    {
        rect.accept((x, y, w, h) ->
        {
            color.accept((cr, cg, cb, ca) -> graphics.setColor(new java.awt.Color(cr, cg, cb, ca)));
            graphics.setStroke(new java.awt.BasicStroke(width.read()));
            graphics.drawRect((int)x, (int)y, (int)w, (int)h);
            graphics.setTransform(transform);
        });
    }
    @Override
    public void fillEllipse(Rectf rect, Color color)
    {
        rect.accept((x, y, w, h) ->
        {
            color.accept((cr, cg, cb, ca) -> graphics.setColor(new java.awt.Color(cr, cg, cb, ca)));
            graphics.translate(x, y);
            graphics.scale(w, h);
            graphics.fillArc(0, 0, 1, 1, 0, 360);
            graphics.setTransform(transform);
        });
    }
}
