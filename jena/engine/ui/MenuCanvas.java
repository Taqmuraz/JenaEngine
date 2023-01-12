package jena.engine.ui;

import jena.engine.common.Action;
import jena.engine.graphics.Color;
import jena.engine.graphics.Text;
import jena.engine.input.Mouse;
import jena.engine.math.Rectf;
import jena.engine.math.RectfStruct;

public class MenuCanvas implements UserCanvas
{
    Canvas canvas;
    Mouse mouse;

    public MenuCanvas(Canvas canvas)
    {
        this.canvas = canvas;
        this.mouse = canvas.mouse();
    }

    @Override
    public Button drawButton(Text text, Rectf rect, Color textColor, Color buttonColor, Action click)
    {
        Color color = c ->
        {
            if (new RectfStruct(rect).contains(mouse.position())) c.call(100, 100, 100, 255);
            else buttonColor.accept(c);
        };
        canvas.fillRect(rect, color);
        canvas.drawText(text, rect, textColor);
        return () -> false;
    }
}