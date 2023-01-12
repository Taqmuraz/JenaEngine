package jena.engine.ui;

import jena.engine.graphics.Color;
import jena.engine.graphics.GraphicsClip;
import jena.engine.graphics.GraphicsDevice;
import jena.engine.graphics.GraphicsDevicePainter;
import jena.engine.graphics.Text;
import jena.engine.math.Rectf;
import jena.engine.math.Matrix3fMul;
import jena.engine.math.Matrix3fTranslation;

public class RootCanvas implements GraphicsDevicePainter
{
    private class Wrapper implements Canvas
    {
        GraphicsClip clip;

        public Wrapper(GraphicsClip clip)
        {
            this.clip = clip;
        }
        @Override
        public void drawText(Text text, Rectf rect, Color color)
        {
            clip.drawText(text, rect, color);
        }
        @Override
        public void fillRect(Rectf rect, Color color)
        {
            clip.fillRect(rect, color);
        }
    }

    Rectf rect;
    CanvasPainter painter;

    public RootCanvas(Rectf rect, CanvasPainter painter)
    {
        this.rect = rect;
        this.painter = painter;
    }

    @Override
    public void paint(GraphicsDevice device)
    {
        device.paintRect(rect, clip ->
            rect.accept((x, y, z, w) -> clip.matrixScope(s ->
                new Matrix3fMul(s, new Matrix3fTranslation(a -> a.call(x, y))), () ->
                    painter.paint(new Wrapper(clip)))));
    }
}