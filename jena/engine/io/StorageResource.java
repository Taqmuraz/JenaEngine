package jena.engine.io;

import jena.engine.common.ErrorHandler;

public interface StorageResource
{
    void read(InputStreamAcceptor acceptor, ErrorHandler errorHandler);
    void write(OutputStreamAcceptor acceptor, ErrorHandler errorHandler);
}