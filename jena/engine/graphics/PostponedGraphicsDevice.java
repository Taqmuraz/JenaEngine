package jena.engine.graphics;

import java.util.ArrayList;
import java.util.List;

import jena.engine.math.Rectf;

public class PostponedGraphicsDevice implements GraphicsDevice, GraphicsDevicePainter
{
    List<GraphicsDevicePainter> painters;

    public PostponedGraphicsDevice(GraphicsDevicePainter painter)
    {
        painters = new ArrayList<GraphicsDevicePainter>();
        painter.paint(this);
    }

    @Override
    public void paintRect(Rectf rect, GraphicsClipPainter painter)
    {
        PostponedGraphicsClip clip = new PostponedGraphicsClip();
        painter.paint(clip);
        painters.add(g -> g.paintRect(rect, clip));
    }

    @Override
    public void paint(GraphicsDevice device)
    {
        for(GraphicsDevicePainter painter : painters) painter.paint(device);
    }
}