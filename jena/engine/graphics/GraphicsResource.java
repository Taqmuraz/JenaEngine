package jena.engine.graphics;

import jena.engine.io.StorageResource;

public interface GraphicsResource
{
    TextureHandle loadTexture(StorageResource file);
}