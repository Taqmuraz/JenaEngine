package jena.engine.io.encoding;

import jena.engine.common.ErrorHandler;
import jena.engine.io.ByteArrayInputFlow;
import jena.engine.io.InputFlowAcceptor;
import jena.engine.io.OutputFlowAcceptor;
import jena.engine.io.StorageResource;

public class VirtualStorageResource implements StorageResource
{
    byte[] bytes;

    public VirtualStorageResource(byte[] bytes)
    {
        this.bytes = bytes;
    }

    @Override
    public void read(InputFlowAcceptor acceptor, ErrorHandler errorHandler)
    {
        acceptor.call(new ByteArrayInputFlow(bytes));
    }

    @Override
    public void write(OutputFlowAcceptor acceptor, ErrorHandler errorHandler)
    {
        errorHandler.call(new Exception("Can't write to virtual storage"));
    }
}