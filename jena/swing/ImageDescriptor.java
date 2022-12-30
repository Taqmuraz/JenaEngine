package jena.swing;

public interface ImageDescriptor
{
    void acceptSize(ImageSizeAcceptor acceptor);
    void acceptImage(ImageAcceptor acceptor);
}