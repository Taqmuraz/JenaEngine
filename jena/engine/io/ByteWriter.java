package jena.engine.io;

import java.io.InputStream;
import java.io.OutputStream;

import jena.engine.common.ErrorHandler;

public class ByteWriter implements OutputStreamAcceptor
{
    InputStream input;
    ErrorHandler errorHandler;

    public ByteWriter(InputStream input, ErrorHandler errorHandler)
    {
        this.input = input;
        this.errorHandler = errorHandler;
    }

    @Override
    public void call(OutputStream output)
    {
        try
        {
            byte[] buffer = new byte[1024];
            int length;
            while ((length = input.read(buffer)) != -1)
            {
                output.write(buffer, 0, length);
            }
        }
        catch(Throwable error)
        {
            errorHandler.call(error);
        }
    }
}