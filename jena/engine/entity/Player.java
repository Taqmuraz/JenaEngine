package jena.engine.entity;

import jena.engine.graphics.GraphicsHandler;
import jena.engine.graphics.GraphicsResource;
import jena.engine.graphics.GraphicsScope;
import jena.engine.graphics.TextureHandle;
import jena.engine.io.StorageFileResource;

public class Player implements GraphicsHandler
{
    private TextureHandle texture0;
    private TextureHandle texture1;

    public Player(GraphicsResource graphicsResource)
    {
        texture0 = graphicsResource.loadTexture(new StorageFileResource("Image.png"));
        texture1 = graphicsResource.loadTexture(new StorageFileResource("Image2.png"));
    }

    @Override
    public void handleGraphics(GraphicsScope graphics)
    {
        graphics.drawSprite(texture0, acceptor -> acceptor.call(0f, 0f, 1f, 1f), acceptor -> acceptor.call(0f, 0f, 1f, 1f));
        graphics.drawSprite(texture1, acceptor -> acceptor.call(0f, 0f, 1f, 1f), acceptor -> acceptor.call(-1f, -1f, 1f, 1f));
    }
}