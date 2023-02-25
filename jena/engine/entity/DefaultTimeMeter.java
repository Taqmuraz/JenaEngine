package jena.engine.entity;

import jena.engine.math.ValueFloat;

public class DefaultTimeMeter implements TimeMeter
{
    private float lastTime;
    private ValueFloat time;

    public DefaultTimeMeter()
    {
        time = new Time();
        time.accept(time -> lastTime = time);
    }

    @Override
    public void measureTime(TimeAcceptor acceptor)
    {
        time.accept(currentTime ->
        {
            float delta = currentTime - lastTime;
            lastTime = currentTime;
            acceptor.call(delta);
        });
    }
}