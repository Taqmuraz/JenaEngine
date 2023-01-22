package jena.engine.io;

import java.io.InputStream;

import jena.engine.common.ErrorHandler;

public interface FileResource
{
    void read(InputStreamAcceptor acceptor, ErrorHandler errorHandler);
    void write(InputStream input, ErrorHandler errorHandler);
}