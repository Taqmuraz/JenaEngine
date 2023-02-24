package jena.engine.io;

import java.io.InputStream;
import java.io.OutputStream;

import jena.engine.common.ErrorHandler;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.File;

public class FileStorageResource implements StorageResource
{
    String fileName;

    public FileStorageResource(String resourceName)
    {
        this.fileName = String.format("resources/%s", resourceName);
    }

    @Override
    public void read(InputFlowAcceptor acceptor, ErrorHandler errorHandler)
    {
        InputStream stream = new NullInputStream();
        
        try
        {
            stream = new FileInputStream(new File(fileName));
        }
        catch (Throwable exception)
        {
            errorHandler.call(exception);
        }
        finally
        {
            try
            {
                acceptor.call(new InputFlowFromStream(stream));
            }
            finally
            {
                try
                {
                    stream.close();
                }
                catch(Throwable closeException) {}
            }
        }
    }

    @Override
    public void write(OutputFlowAcceptor acceptor, ErrorHandler errorHandler)
    {
        OutputStream output = null;
        try
        {
            output = new FileOutputStream(new File(fileName));
            acceptor.call(new OutputFlowFromStream(output));
        }
        catch (Throwable exception)
        {
            errorHandler.call(exception);
        }
        finally
        {
            if (output != null)
            {
                try
                {
                    output.close();
                }
                catch(Throwable closeException) {}
            }
        }
    }
}