package jena.swing;

import java.awt.Graphics2D;
import java.util.Stack;

import jena.engine.common.Action;
import jena.engine.graphics.Color;
import jena.engine.graphics.GraphicsClip;
import jena.engine.graphics.TextureHandle;
import jena.engine.graphics.Transformation;
import jena.engine.math.Matrix3f;
import jena.engine.math.Matrix3fElements;
import jena.engine.math.Matrix3fStruct;
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
    private Stack<Matrix3f> matrixStack;
    private AffineTransform transform;
    private AffineTransform identity;
    private AffineTransform line;
    private AffineTransform buffer;
    private Shape clip;

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
        matrixStack = new Stack<Matrix3f>();
        reset();
    }

	@Override
	public void drawSprite(TextureHandle texture, Rectf source, Rectf destination)
	{
        if (texture instanceof SwingTextureResource swingTexture)
        {
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
    public void matrixScope(Transformation transformation, Action action)
    {
        Matrix3f matrix = transformation.transform(matrixStack.empty() ? new Matrix3fStruct() : matrixStack.peek());
        pushMatrix(matrix);
        action.call();
        popMatrix();
    }

	private void pushMatrix(Matrix3f matrix) 
	{
		matrixStack.push(matrix);
        updateTransform(matrix);
	}

    private void updateTransform(Matrix3f matrix)
    {
        Matrix3fElements e = matrix.elements();
        transform.setTransform(e.at(0), e.at(1), e.at(3), e.at(4), e.at(6), e.at(7));
        graphics.setTransform(transform);
    }

    private void reset()
    {
        graphics.setClip(clip);
        transform.setToIdentity();
        graphics.setTransform(transform);
    }

	private void popMatrix() 
	{
		matrixStack.pop();
        updateTransform(matrixStack.empty() ? new Matrix3fStruct() : matrixStack.peek());
	}
    @Override
    public void drawLine(Vector2f a, Vector2f b, Color color, ValueFloat width)
    {
        a.accept((ax, ay) -> b.accept((bx, by) ->
        {
            color.accept((cr, cg, cb, ca) -> graphics.setColor(new java.awt.Color(cr, cg, cb, ca)));
            graphics.setStroke(new java.awt.BasicStroke(width.read()));
            Vector2fStruct d = new Vector2fNormalized(v -> v.call(bx - ax, by - ay));
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
