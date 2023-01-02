package jena.engine.graphics;

import jena.engine.math.Rectf;
import jena.engine.common.FunctionSingle;
import jena.engine.math.Matrix3f;

public interface GraphicsScope
{
    void drawSprite(TextureHandle texture, Rectf source, Rectf destination);
    void fillRect(Rectf rect, Color color);
    void pushMatrix(FunctionSingle<Matrix3f, Matrix3f> transformation);
    void popMatrix();
}