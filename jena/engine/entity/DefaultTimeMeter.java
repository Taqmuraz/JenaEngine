package jena.engine.entity;

public class DefaultTimeMeter implements TimeMeter
{
    private float lastTime;

    public DefaultTimeMeter()
    {
        Time.accept(time -> lastTime = time);
    }

    @Override
    public void measureTime(TimeAcceptor acceptor)
    {
        Time.accept(currentTime ->
        {
            float delta = currentTime - lastTime;
            lastTime = currentTime;
            acceptor.call(delta);
        });
    }
}