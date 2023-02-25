package jena.engine.entity;

import jena.engine.math.FloatAcceptor;
import jena.engine.math.ValueFloat;

public class FrameDeltaTime implements ValueFloat
{
    private float lastTime;
    private ValueFloat time;

    public FrameDeltaTime(ValueFloat time)
    {
        this.time = time;
        time.accept(t -> lastTime = t);
    }

    @Override
    public void accept(FloatAcceptor acceptor)
    {
        time.accept(currentTime ->
        {
            float delta = currentTime - lastTime;
            lastTime = currentTime;
            acceptor.call(delta);
        });
    }
}