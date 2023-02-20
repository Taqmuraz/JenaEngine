package jena.engine.math;

public interface Vector2f
{
    void accept(Vector2fAcceptor acceptor);

    default Vector2f mul(ValueFloat f)
    {
        return new Vector2fMul(this, f);
    }
    default Vector2f normalized()
    {
        return new Vector2fNormalized(this);
    }
    default Vector2f sub(Vector2f v)
    {
        return new Vector2fSub(this, v);
    }
    default Vector2f add(Vector2f v)
    {
        return new Vector2fAdd(this, v);
    }
    default int compareLength(Vector2f v)
    {
        float[] buffer = new float[2];
        new Vector2fLength(this).accept(l -> buffer[0] = l);
        new Vector2fLength(v).accept(l -> buffer[1] = l);
        return Float.compare(buffer[0], buffer[1]);
    }
}