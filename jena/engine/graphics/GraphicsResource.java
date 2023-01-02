package jena.engine.graphics;

import jena.engine.io.FileResource;

public interface GraphicsResource
{
    TextureHandle loadTexture(FileResource file);
}