package jena.engine.graphics;

import jena.engine.math.Rectf;
import jena.engine.common.Action;
import jena.engine.common.FunctionSingle;
import jena.engine.math.Matrix3f;

public interface GraphicsClip
{
    void drawSprite(TextureHandle texture, Rectf source, Rectf destination);
    void fillRect(Rectf rect, Color color);
    void matrixScope(FunctionSingle<Matrix3f, Matrix3f> transformation, Action paint);
}