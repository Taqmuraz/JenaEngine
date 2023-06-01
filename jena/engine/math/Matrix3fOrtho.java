package jena.engine.math;

public class Matrix3fOrtho implements Matrix3f
{
    private Vector2f clipSize;
    private ValueFloat worldScale;

    public Matrix3fOrtho(Vector2f clipSize, ValueFloat worldScale)
    {
        this.clipSize = clipSize;
        this.worldScale = worldScale;
    }

    @Override
    public void accept(Matrix3fAcceptor acceptor)
    {
        clipSize.accept((sx, sy) -> worldScale.accept(scale -> acceptor.call(index ->
        {
            float dScale = 1f / scale;
            if (sx > sy)
            {
                switch (index)
                {
                    case 0: return (sy / sx) * dScale;
                    case 4: return dScale;
                    case 8: return 1f;
                    default: return 0f;
                }
            }
            else
            {
                switch (index)
                {
                    case 0: return dScale;
                    case 4: return (sx / sy) * dScale;
                    case 8: return 1f;
                    default: return 0f;
                }
            }
        })));
    }
}