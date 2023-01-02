package jena.engine.entity;

import java.time.Duration;
import java.time.LocalTime;
import java.time.temporal.Temporal;

public class Time
{
    public static void acceptTime(TimeAcceptor acceptor)
    {
        Temporal currentFrame = LocalTime.now();
		Duration frameTime = Duration.between(LocalTime.MIN, currentFrame);
		
		acceptor.call((float)(frameTime.getSeconds() + frameTime.getNano() * 0.000000001));
    }
}