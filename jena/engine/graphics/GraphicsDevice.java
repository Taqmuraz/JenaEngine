package jena.engine.graphics;

import jena.engine.math.Rectf;

public interface GraphicsDevice
{
    void paintRect(Rectf rect, GraphicsClipPainter painter);
}