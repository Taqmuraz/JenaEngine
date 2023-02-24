package jena.swing;

import java.awt.image.BufferedImage;
import java.awt.TexturePaint;

import javax.imageio.ImageIO;
import jena.engine.common.ErrorHandler;
import jena.engine.io.InputStreamFromFlow;
import jena.engine.io.StorageResource;

public class FileImageResource implements SwingTextureResource
{
    private BufferedImage image;
    private static final BufferedImage nullImage = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);
    private TexturePaint paint;

    public FileImageResource(StorageResource file, ErrorHandler errorHandler)
    {
        file.read(stream ->
        {
            try 
            {
                image = ImageIO.read(new InputStreamFromFlow(stream));
            }
            catch(Throwable error) 
            {
                errorHandler.call(error);
            }
        }, errorHandler);

        image = image == null ? nullImage : image;
        paint = new TexturePaint(image, new java.awt.geom.Rectangle2D.Float(0f, 0f, 1f, 1f));
    }

    @Override
    public void accept(ImageDescriptorAcceptor acceptor) 
    {
        acceptor.call(new ImageDescriptor()
        {
            @Override
            public void acceptPaint(PaintAcceptor acceptor)
            {
                acceptor.call(paint);
            }
            @Override
            public void acceptImage(ImageAcceptor acceptor)
            {
                acceptor.call(image);
            }
            @Override
            public void acceptSize(ImageSizeAcceptor acceptor)
            {
                acceptor.call(image.getWidth(), image.getHeight());
            }
        });
    }
}