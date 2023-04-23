package jena.engine.ui;

import jena.engine.graphics.Color;
import jena.engine.graphics.ColorStruct;
import jena.engine.graphics.CompositeGraphicsDrawing;
import jena.engine.graphics.GraphicsBrush;
import jena.engine.graphics.GraphicsDevice;
import jena.engine.graphics.GraphicsDevicePainter;
import jena.engine.graphics.GraphicsDrawing;
import jena.engine.graphics.GraphicsDrawingPainter;
import jena.engine.graphics.MatrixScopeGraphicsPainter;
import jena.engine.graphics.Text;
import jena.engine.input.ClipMouse;
import jena.engine.input.Mouse;
import jena.engine.math.Rectf;
import jena.engine.math.ValueFloatStruct;
import jena.engine.math.Vector2fStruct;
import jena.engine.math.Matrix3f;
import jena.engine.math.Matrix3fTranslation;

public class RootCanvas implements GraphicsDevicePainter
{
    private class Wrapper implements Canvas
    {
        GraphicsBrush clip;

        public Wrapper(GraphicsBrush clip)
        {
            this.clip = clip;
        }
        @Override
        public GraphicsDrawing drawText(Text text, Rectf rect, Color color)
        {
            return clip.drawText(text, rect, color);
        }
        @Override
        public GraphicsDrawing fillRect(Rectf rect, Color color)
        {
            float outline = 1f;
            return new CompositeGraphicsDrawing(
                clip.fillRect(rect, color),
                clip.drawRect(a -> rect.accept((rx, ry, rw, rh) ->
                {
                    a.call(rx - outline, ry - outline, rw + outline * 2f, rh + outline * 2f);
                }),
                new ColorStruct(0, 0, 0, 255), new ValueFloatStruct(outline * 2f)));
        }
        @Override
        public Mouse mouse()
        {
            return mouse;
        }
    }

    Rectf rect;
    CanvasPainter painter;
    Mouse mouse;

    public RootCanvas(Rectf rect, Mouse mouse, CanvasPainter painter)
    {
        this.rect = rect;
        this.mouse = new ClipMouse(rect, mouse);
        this.painter = painter;
    }

    @Override
    public GraphicsDrawing paint(GraphicsDevice device)
    {
        Matrix3f matrix = a -> rect.accept((x, y, z, w) ->
            new Matrix3fTranslation(new Vector2fStruct(x, y)).accept(a));
        return device.paintRect(rect, clip ->
            new MatrixScopeGraphicsPainter(
                matrix,
                new GraphicsDrawingPainter(
                    painter.paint(new Wrapper(clip)))));
    }
}