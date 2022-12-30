package jena.engine.io;

import java.io.InputStream;

public interface InputStreamAcceptor
{
    void call(InputStream inputStream);
}