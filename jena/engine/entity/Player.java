package jena.engine.entity;

import jena.engine.graphics.GraphicsClipPainter;
import jena.engine.graphics.GraphicsResource;
import jena.engine.graphics.GraphicsClip;
import jena.engine.graphics.TextureHandle;
import jena.engine.io.StorageFileResource;
import jena.engine.math.Matrix3fMul;
import jena.engine.math.Matrix3fTransform;

public class Player implements GraphicsClipPainter
{
    private TextureHandle texture0;
    private TextureHandle texture1;

    public Player(GraphicsResource graphicsResource)
    {
        texture0 = graphicsResource.loadTexture(new StorageFileResource("Image.png"));
        texture1 = graphicsResource.loadTexture(new StorageFileResource("Image2.png"));
    }

    @Override
    public void paint(GraphicsClip graphics)
    {
        graphics.drawSprite(texture0, acceptor -> acceptor.call(0f, 0f, 1f, 1f), acceptor -> acceptor.call(0f, 0f, 1f, 1f));
        
        Time.acceptTime(time -> graphics.matrixScope(
            source -> new Matrix3fMul(source, new Matrix3fTransform(a -> a.call(-0.5f, -0.5f), time)),
            () -> graphics.drawSprite(texture1,
                acceptor -> acceptor.call(0f, 0f, 1f, 1f),
                acceptor -> acceptor.call(-0.25f, -0.25f, 0.5f, 0.5f))));
    }
}