package jena.engine.graphics;

import jena.engine.math.Rectf;
import jena.engine.math.ValueFloat;
import jena.engine.math.Vector2f;

public interface GraphicsBrush
{
    GraphicsDrawing drawSprite(TextureHandle texture, Rectf source, Rectf destination);
    GraphicsDrawing drawTile(TextureHandle texture, Vector2f tiles, Rectf destination);
    GraphicsDrawing drawLine(Vector2f a, Vector2f b, Color color, ValueFloat width);
    GraphicsDrawing drawEllipse(Rectf rect, Color color, ValueFloat width);
    GraphicsDrawing drawRect(Rectf rect, Color color, ValueFloat width);
    GraphicsDrawing drawText(Text text, Rectf rect, Color color);
    GraphicsDrawing fillEllipse(Rectf rect, Color color);
    GraphicsDrawing fillRect(Rectf rect, Color color);
}