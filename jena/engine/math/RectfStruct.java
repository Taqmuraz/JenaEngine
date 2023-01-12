package jena.engine.math;

public final class RectfStruct implements Rectf
{
    public float x, y, width, height;

    public RectfStruct(float x, float y, float width, float height)
    {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }
    public RectfStruct(Rectf rect)
    {
        rect.accept((x, y, w, h) ->
        {
            this.x = x;
            this.y = y;
            this.width = w;
            this.height = h;
        });
    }

    @Override
    public void accept(RectfAcceptor acceptor)
    {
        acceptor.call(x, y, width, height);
    }
}