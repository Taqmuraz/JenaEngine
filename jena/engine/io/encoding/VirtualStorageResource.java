package jena.engine.io.encoding;

import java.io.ByteArrayInputStream;

import jena.engine.common.ErrorHandler;
import jena.engine.io.InputStreamAcceptor;
import jena.engine.io.OutputStreamAcceptor;
import jena.engine.io.StorageResource;

public class VirtualStorageResource implements StorageResource
{
    byte[] bytes;

    public VirtualStorageResource(byte[] bytes)
    {
        this.bytes = bytes;
    }

    @Override
    public void read(InputStreamAcceptor acceptor, ErrorHandler errorHandler)
    {
        acceptor.call(new ByteArrayInputStream(bytes));
    }

    @Override
    public void write(OutputStreamAcceptor acceptor, ErrorHandler errorHandler)
    {
        errorHandler.call(new Exception("Can't write to virtual storage"));
    }
}