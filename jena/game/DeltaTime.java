package jena.game;

import jena.engine.math.FloatAcceptor;
import jena.engine.math.ValueFloat;

public class DeltaTime implements ValueFloat
{
    private float lastTime;
    private ValueFloat time;

    public DeltaTime(ValueFloat time)
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