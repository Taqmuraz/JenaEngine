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
    public void read(InputStreamAcceptor acceptor, ErrorHandler errorHandler)
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
                acceptor.call(stream);
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
    public void write(InputStream input, ErrorHandler errorHandler)
    {
        OutputStream output = null;
        try
        {
            output = new FileOutputStream(new File(fileName));
            byte[] buffer = new byte[1024];
            int length;
            while ((length = input.read(buffer)) != -1)
            {
                output.write(buffer, 0, length);
            }
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