package jena.swing;

import java.awt.Image;
import java.awt.image.ImageObserver;

public class DefaultImageObserver implements ImageObserver
{
    public static final ImageObserver instance = new DefaultImageObserver();

    @Override
    public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) 
    {
        return false;
    }
}