package jena.engine.ui;

import jena.engine.common.Action;
import jena.engine.graphics.Color;
import jena.engine.graphics.Text;
import jena.engine.math.Rectf;

public interface UserCanvas
{
    void drawButton(Text text, Rectf rect, Color textColor, Color buttonColor, Action click);
}