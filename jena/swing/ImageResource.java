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
		public void accept(SwingTextureResourceAcceptor acceptor)
		{
			acceptor.call(image);
		}
}