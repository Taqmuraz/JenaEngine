package jena.swing;

import java.awt.Graphics2D;
import java.util.Stack;

import jena.engine.common.Action;
import jena.engine.common.FunctionSingle;
import jena.engine.graphics.GraphicsClip;
import jena.engine.graphics.TextureHandle;
import jena.engine.math.Matrix3f;
import jena.engine.math.Matrix3fStruct;
import jena.engine.math.Rectf;

import java.awt.geom.AffineTransform;
import java.awt.Shape;

public class SwingGraphicsClip implements GraphicsClip
{
	private Graphics2D graphics;
    private Stack<Matrix3f> matrixStack;
    private AffineTransform transform;
    private Shape clip;

    public SwingGraphicsClip(Graphics2D graphics)
    {
        this(graphics, graphics.getClip());
    }
    public SwingGraphicsClip(Graphics2D graphics, Shape clip)
    {
        this.graphics = graphics;
        this.clip = clip;
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
                            AffineTransform copy = graphics.getTransform();
                            graphics.translate(dx, dy);
                            graphics.scale(dw, dh);
                            graphics.drawImage(image,
                                    0, 1, 1, 0,
                                    (int)(sx * iw), (int)(sy * ih), (int)((sx + sw) * iw), (int)((sy + sh) * ih),
                                    null);
                            graphics.setTransform(copy);
                        });
                    }));
            }));
        }
	}

	@Override
	public void fillRect(Rectf rect, jena.engine.graphics.Color color)
	{
		rect.accept((x, y, w, h) ->
            color.acceptInts((cr, cg, cb, ca) ->
            {
                graphics.setBackground(new java.awt.Color(cr, cg, cb, ca));
                graphics.clearRect((int)x, (int)y, (int)w, (int)h);
            }));
	}

    @Override
    public void matrixScope(FunctionSingle<Matrix3f, Matrix3f> transformation, Action action)
    {
        Matrix3f matrix = transformation.call(matrixStack.empty() ? new Matrix3fStruct() : matrixStack.peek());
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
        matrix.accept(e -> transform.setTransform(e[0], e[1], e[3], e[4], e[6], e[7]));
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
}
