package jena.engine.io;

import java.io.OutputStream;

public interface OutputStreamAcceptor
{
    void call(OutputStream output);
}