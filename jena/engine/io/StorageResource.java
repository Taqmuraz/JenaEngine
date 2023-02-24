package jena.engine.io;

import jena.engine.common.ErrorHandler;

public interface StorageResource
{
    void read(InputFlowAcceptor acceptor, ErrorHandler errorHandler);
    void write(OutputFlowAcceptor acceptor, ErrorHandler errorHandler);
}