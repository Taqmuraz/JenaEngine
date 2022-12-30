package jena.swing;

import jena.engine.graphics.TextureHandle;

public interface SwingTextureResource extends TextureHandle
{
	void accept(ImageDescriptorAcceptor acceptor);
}