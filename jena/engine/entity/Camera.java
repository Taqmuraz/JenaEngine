package jena.engine.entity;

import jena.engine.graphics.Color;
import jena.engine.graphics.GraphicsHandler;
import jena.engine.graphics.GraphicsScope;
import jena.engine.math.Matrix3fMul;
import jena.engine.math.Matrix3fViewport;
import jena.engine.math.Rectf;
import jena.engine.math.Vector2f;

public class Camera implements GraphicsHandler
{
    private GraphicsHandler scene;
    private Color clearColor;
    private Rectf clearRect;
    private Vector2f size;

    public Camera(Vector2f size, Color clearColor, GraphicsHandler scene)
    {
        this.clearColor = clearColor;
        this.scene = scene;
        this.size = size;
        clearRect = acceptor -> size.accept((w, h) ->  acceptor.call(0f, 0f, w, h));
    }

    @Override
    public void handleGraphics(GraphicsScope graphics)
    {
        graphics.fillRect(clearRect, clearColor);
        size.accept((sizeX, sizeY) -> graphics.matrixScope(
            source -> new Matrix3fMul(source, new Matrix3fViewport(sizeX, sizeY)),
            () -> scene.handleGraphics(graphics)));
    }
}