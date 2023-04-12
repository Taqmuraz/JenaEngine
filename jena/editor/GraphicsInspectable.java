package jena.editor;

import jena.engine.graphics.GraphicsPainter;

public interface GraphicsInspectable
{
    GraphicsPainter inspect(GraphicsInspector inspector);
}