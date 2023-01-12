package jena.engine.ui;

import jena.engine.common.Action;
import jena.engine.graphics.Color;
import jena.engine.graphics.Text;
import jena.engine.math.Rectf;

public class MenuCanvas implements UserCanvas
{
    Canvas canvas;

    public MenuCanvas(Canvas canvas)
    {
        this.canvas = canvas;
    }

    @Override
    public Button drawButton(Text text, Rectf rect, Color textColor, Color buttonColor, Action click)
    {
        canvas.fillRect(rect, buttonColor);
        canvas.drawText(text, rect, textColor);
        return () -> false;
    }
}