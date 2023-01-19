package jena.engine.graphics;

import jena.engine.math.Rectf;
import jena.engine.math.RectfAcceptor;
import jena.engine.math.Vector2f;

public class GraphicsRectf implements Rectf
{
    Vector2f screenSize;
    Vector2f graphicsSize;

    public GraphicsRectf(Vector2f screenSize, Vector2f graphicsSize)
    {
        this.screenSize = screenSize;
        this.graphicsSize = graphicsSize;
    }

    @Override
    public void accept(RectfAcceptor acceptor)
    {
        screenSize.accept((screenWidth, screenHeight) -> graphicsSize.accept((graphicsWidth, graphicsHeight) ->
        {
            int w = (int)screenWidth;
            int h = (int)screenHeight;
            float mw = screenWidth / graphicsWidth;
            float mh = screenHeight / graphicsHeight;

            int rx, ry, rw, rh;

            if (mw < mh)
            {
                rw = (int) (graphicsWidth * mw);
                rh = (int) (graphicsHeight * mw);
                rx = 0;
                ry = (h - rh) >>> 1;
            }
            else
            {
                rw = (int) (graphicsWidth * mh);
                rh = (int) (graphicsHeight * mh);
                rx = (w - rw) >>> 1;
                ry = 0;
            }

            acceptor.call(rx, ry, rw, rh);
        }));
    }
    
}