package jena.swing;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import jena.engine.common.ErrorHandler;
import jena.engine.io.FileResource;

public class FileImageResource implements SwingTextureResource
{
	private BufferedImage image;
    private static final BufferedImage nullImage = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);

	public FileImageResource(FileResource file, ErrorHandler errorHandler)
	{
		file.read(stream ->
		{
			try 
            {
                image = ImageIO.read(stream);
            }
            catch(Throwable error) 
            {
                errorHandler.call(error);
            }
		}, error ->
		{
			errorHandler.call(error);
		});

        image = image == null ? nullImage : image;
	}

    @Override
    public void accept(ImageDescriptorAcceptor acceptor) 
    {
        acceptor.call(new ImageDescriptor()
        {
            @Override
            public void acceptSize(ImageSizeAcceptor acceptor)
            {
                acceptor.call(image.getWidth(), image.getHeight());
            }

            @Override
            public void acceptImage(ImageAcceptor acceptor)
            {
                acceptor.call(image);
            }
        });
    }
}