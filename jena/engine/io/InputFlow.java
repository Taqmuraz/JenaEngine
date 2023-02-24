package jena.engine.io;

public interface InputFlow
{
    byte next();
    boolean hasNext();
}