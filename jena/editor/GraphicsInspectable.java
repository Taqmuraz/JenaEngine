package jena.editor;

import jena.engine.graphics.GraphicsClipPainter;

public interface GraphicsInspectable
{
    GraphicsClipPainter inspect(GraphicsInspector inspector);
}