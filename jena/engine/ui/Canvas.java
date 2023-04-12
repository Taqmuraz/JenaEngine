package jena.engine.ui;

import jena.engine.graphics.Color;
import jena.engine.graphics.GraphicsDrawing;
import jena.engine.graphics.Text;
import jena.engine.input.Mouse;
import jena.engine.math.Rectf;

public interface Canvas
{
    GraphicsDrawing drawText(Text text, Rectf rect, Color color);
    GraphicsDrawing fillRect(Rectf rect, Color color);
    Mouse mouse();
}