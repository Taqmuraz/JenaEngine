package jena.swing;

import java.awt.Image;

public class ImageResource implements SwingTextureResource
{
    Image image;

    public ImageResource(Image image)
    {
        this.image = image;
    }

    @Override
    public void accept(ImageDescriptorAcceptor acceptor)
    {
        acceptor.call(new ImageDescriptor()
        {
            @Override
            public void acceptSize(ImageSizeAcceptor acceptor)
            {
                acceptor.call(image.getWidth(DefaultImageObserver.instance), image.getHeight(DefaultImageObserver.instance));
            }

            @Override
            public void acceptImage(ImageAcceptor acceptor)
            {
                acceptor.call(image);
            }
        });
    }
}