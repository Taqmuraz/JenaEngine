package jena.swing;

import java.util.ArrayList;
import java.util.List;

import jena.engine.graphics.GraphicsClipPainter;
import jena.engine.graphics.GraphicsDevice;
import jena.engine.graphics.GraphicsDevicePainter;
import jena.engine.math.Rectf;

public class PostponedGraphicsDevice implements GraphicsDevice, GraphicsDevicePainter
{
    List<GraphicsDevicePainter> painters;

    public PostponedGraphicsDevice()
    {
        painters = new ArrayList<GraphicsDevicePainter>();
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