package jena.engine.math;

public final class RectfStruct implements Rectf
{
    public float x, y, width, height;

    public RectfStruct()
    {
    }

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

    public boolean contains(Vector2f point)
    {
        Vector2fStruct p = new Vector2fStruct(point);
        return p.x >= x && p.y >= y && p.x < (x + width) && p.y < (y + height);
    }

    public void apply(Rectf rect)
    {
        rect.accept((x, y, w, h) ->
        {
            this.x = x;
            this.y = y;
            width = w;
            height = h;
        });
    }
}