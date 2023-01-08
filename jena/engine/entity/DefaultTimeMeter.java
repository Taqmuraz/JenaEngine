package jena.engine.entity;

public class DefaultTimeMeter implements TimeMeter
{
    private float lastTime;

    public DefaultTimeMeter()
    {
        lastTime = Time.time();
    }

    @Override
    public float measureTime()
    {
        float currentTime = Time.time();
        float delta = currentTime - lastTime;
        lastTime = currentTime;
        return delta;
    }
}