package jena.engine.graphics;

import jena.engine.math.Rectf;
import jena.engine.math.ValueFloat;
import jena.engine.math.Vector2f;
import jena.engine.common.Action;
import jena.engine.common.FunctionSingle;
import jena.engine.math.Matrix3f;

public interface GraphicsClip
{
    void drawSprite(TextureHandle texture, Rectf source, Rectf destination);
    void drawLine(Vector2f a, Vector2f b, Color color, ValueFloat width);
    void drawEllipse(Rectf rect, Color color, ValueFloat width);
    void fillEllipse(Rectf rect, Color color);
    void fillRect(Rectf rect, Color color);
    void matrixScope(FunctionSingle<Matrix3f, Matrix3f> transformation, Action paint);
}