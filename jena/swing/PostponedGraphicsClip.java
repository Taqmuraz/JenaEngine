package jena.swing;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import jena.engine.common.Action;
import jena.engine.graphics.Color;
import jena.engine.graphics.GraphicsClip;
import jena.engine.graphics.GraphicsClipPainter;
import jena.engine.graphics.Text;
import jena.engine.graphics.TextureHandle;
import jena.engine.graphics.Transformation;
import jena.engine.math.Rectf;
import jena.engine.math.ValueFloat;
import jena.engine.math.Vector2f;

public class PostponedGraphicsClip implements GraphicsClip, GraphicsClipPainter
{
    private class MatrixScope implements GraphicsClipPainter
    {
        List<GraphicsClipPainter> painters;
        Transformation transformation;

        MatrixScope(Transformation transformation)
        {
            painters = new ArrayList<GraphicsClipPainter>();
            this.transformation = transformation;
        }

        public void appendPainter(GraphicsClipPainter painter)
        {
            painters.add(painter);
        }

        public void paint(GraphicsClip clip)
        {
            clip.matrixScope(transformation, () ->
            {
                for(GraphicsClipPainter painter : painters) painter.paint(clip);
            });
        }
    }

    MatrixScope root;
    Stack<MatrixScope> scopes;

    public PostponedGraphicsClip()
    {
        scopes = new Stack<MatrixScope>();
        root = new MatrixScope(s -> s);
        scopes.push(root);
    }

    @Override
    public void paint(GraphicsClip clip)
    {
        root.paint(clip);
    }

    @Override
    public void drawSprite(TextureHandle texture, Rectf source, Rectf destination)
    {
        MatrixScope scope = scopes.peek();
        scope.appendPainter(clip -> clip.drawSprite(texture, source, destination));
    }

    @Override
    public void drawLine(Vector2f a, Vector2f b, Color color, ValueFloat width)
    {
        scopes.peek().appendPainter(clip -> clip.drawLine(a, b, color, width));
    }

    @Override
    public void drawEllipse(Rectf rect, Color color, ValueFloat width)
    {
        scopes.peek().appendPainter(clip -> clip.drawEllipse(rect, color, width));
    }

    @Override
    public void fillEllipse(Rectf rect, Color color)
    {
        scopes.peek().appendPainter(clip -> clip.fillEllipse(rect, color));
    }

    @Override
    public void fillRect(Rectf rect, Color color)
    {
        scopes.peek().appendPainter(clip -> clip.fillRect(rect, color));
    }

    @Override
    public void drawText(Text text, Rectf rect, Color color)
    {
        scopes.peek().appendPainter(clip -> clip.drawText(text, rect, color));
    }

    @Override
    public void matrixScope(Transformation transformation, Action paint)
    {
        MatrixScope newScope = new MatrixScope(transformation);
        scopes.peek().appendPainter(newScope);
        scopes.push(newScope);
        paint.call();
        scopes.pop();
    }
}