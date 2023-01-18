package jena.swing;

public interface ImageDescriptor
{
    void acceptPaint(PaintAcceptor acceptor);
    void acceptImage(ImageAcceptor acceptor);
    void acceptSize(ImageSizeAcceptor acceptor);
}