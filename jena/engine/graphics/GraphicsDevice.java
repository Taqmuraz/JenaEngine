package jena.engine.graphics;

import jena.engine.math.*;

public interface GraphicsDevice 
{
    void drawSprite(TextureHandle texture, Rectf source, Matrix3f transform);
}