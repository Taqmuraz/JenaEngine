package jena.engine.io.encoding;

import jena.engine.common.ErrorHandler;
import jena.engine.io.StorageResource;

public class FileDecoder
{
    StorageResource input;

    public FileDecoder(StorageResource input)
    {
        this.input = input;
    }

    public void decode(Decodable decodable, ErrorHandler errorHandler)
    {
        input.read(stream ->
        {
            decodable.decode(length ->
            {
                byte[] buffer = new byte[length];
                try
                {
                    stream.read(buffer);
                }
                catch(Throwable error)
                {
                    errorHandler.call(error);
                }
                return buffer;
            });
        },
        errorHandler);
    }
}