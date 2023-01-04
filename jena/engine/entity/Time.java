package jena.engine.entity;

import java.time.Duration;
import java.time.LocalTime;
import java.time.temporal.Temporal;

public class Time
{
    public static void acceptTime(TimeAcceptor acceptor)
    {
		acceptor.call(time());
    }
    public static float time()
    {
        Temporal currentFrame = LocalTime.now();
		Duration frameTime = Duration.between(LocalTime.MIN, currentFrame);
		
		return (float)(frameTime.getSeconds() + frameTime.getNano() * 0.000000001);
    }
}