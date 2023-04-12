package jena.engine.ui;

import jena.engine.common.Action;
import jena.engine.graphics.Color;
import jena.engine.graphics.CompositeGraphicsDrawing;
import jena.engine.graphics.GraphicsDrawing;
import jena.engine.graphics.Text;
import jena.engine.input.Key;
import jena.engine.input.Mouse;
import jena.engine.math.Rectf;
import jena.engine.math.RectfStruct;
import jena.engine.math.Vector2f;

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
    public GraphicsDrawing drawButton(Text text, Rectf rect, Color textColor, Color buttonColor, Action click)
    {
        Key button = mouse.button(0);
        Vector2f mousePosition = mouse.position();

        Color color = c ->
        {
            if (new RectfStruct(rect).contains(mousePosition))
            {
                if (button.isDown()) click.call();
                if (button.isHold()) c.call(150, 200, 150, 255);
                else c.call(150, 150, 150, 255);
            }
            else buttonColor.accept(c);
        };
        return new CompositeGraphicsDrawing(
            canvas.fillRect(rect, color),
            canvas.drawText(text, rect, textColor));
    }
}