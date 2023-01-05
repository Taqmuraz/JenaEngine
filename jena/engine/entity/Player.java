package jena.engine.entity;

import jena.engine.graphics.GraphicsClipPainter;
import jena.engine.graphics.GraphicsResource;
import jena.engine.graphics.GraphicsClip;
import jena.engine.graphics.TextureHandle;
import jena.engine.io.StorageFileResource;
import jena.engine.math.Matrix3f;
import jena.engine.math.Matrix3fBuilder;
import jena.engine.math.Matrix3fMul;
import jena.engine.math.Matrix3fTransform;
import jena.engine.math.Rectf;

public class Player implements GraphicsClipPainter
{
    private class BodyPart implements GraphicsClipPainter
    {
        Rectf source;
        Rectf destination;
        Matrix3f transform;
        GraphicsClipPainter[] children;

        public BodyPart(Rectf source, Rectf destination, Matrix3f transform, GraphicsClipPainter...children)
        {
            this.source = source;
            this.destination = destination;
            this.transform = transform;
            this.children = children;
        }

        @Override
        public void paint(GraphicsClip clip)
        {
            clip.matrixScope(source -> new Matrix3fMul(source, transform), () ->
            {
                clip.drawSprite(texture, source, destination);
                for(GraphicsClipPainter child : children)
                {
                    child.paint(clip);
                }
            });
        }
    }

    private TextureHandle texture;
    private GraphicsClipPainter root;

    public Player(GraphicsResource graphicsResource)
    {
        texture = graphicsResource.loadTexture(new StorageFileResource("HumanMap.png"));

        BodyPart shoe = new BodyPart(a -> a.call(0.8f, 0.5f, 0.2f, 0.5f), a -> a.call(-0.5f, -0.8f, 1f, 1f), new Matrix3fTransform(0f, -0.5f, 0f));
        BodyPart knee = new BodyPart(a -> a.call(0.4f, 0.5f, 0.2f, 0.5f), a -> a.call(-0.5f, -0.8f, 1f, 1f), new Matrix3fTransform(0f, -0.8f, 0f), shoe);
        BodyPart legL = new BodyPart(a -> a.call(0.4f, 0f, 0.2f, 0.5f), a -> a.call(-0.5f, -0.8f, 1f, 1.3f), a -> new Matrix3fTransform(-0.15f, -0.45f, 0.5f, 0.5f, -(float)Math.sin(Time.time() * 2f) * 0.5f).accept(a), knee);
        BodyPart legR = new BodyPart(a -> a.call(0.4f, 0f, 0.2f, 0.5f), a -> a.call(-0.5f, -0.8f, 1f, 1.3f), a -> new Matrix3fTransform(0.1f, -0.45f, 0.5f, 0.5f, (float)Math.sin(Time.time() * 2f) * 0.5f).accept(a), knee);
        BodyPart body = new BodyPart(a -> a.call(0f, 0f, 0.4f, 1f), a -> a.call(-0.5f, -0.5f, 0.8f, 1.2f), new Matrix3fTransform(0f, 0f, 1f, 1f, 0f));
        root = clip ->
        {
            clip.matrixScope(source -> new Matrix3fBuilder(source).scale(a -> a.call(0.25f, 0.25f)).build(), () ->
            {
                legR.paint(clip);
                body.paint(clip);
                legL.paint(clip);
            });
        };
    }

    @Override
    public void paint(GraphicsClip clip)
    {
        root.paint(clip);
    }
}