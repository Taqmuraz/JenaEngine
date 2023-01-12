package jena.engine.ui;

import jena.engine.common.Action;
import jena.engine.graphics.Color;
import jena.engine.graphics.Text;
import jena.engine.input.Key;
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
            Key m = mouse.button(0);
            if (new RectfStruct(rect).contains(mouse.position()))
            {
                if (m.isDown()) click.call();
                if (m.isHold()) c.call(150, 200, 150, 255);
                else c.call(150, 150, 150, 255);
            }
            else buttonColor.accept(c);
        };
        canvas.fillRect(rect, color);
        canvas.drawText(text, rect, textColor);
        return () -> false;
    }
}