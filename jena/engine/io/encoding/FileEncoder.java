package jena.engine.io.encoding;

import jena.engine.common.ErrorHandler;
import jena.engine.io.StorageResource;

public class FileEncoder
{
    StorageResource output;

    public FileEncoder(StorageResource output)
    {
        this.output = output;
    }

    public void encode(Encodable encodable, ErrorHandler errorHandler)
    {
        output.write(stream ->
        {
            encodable.encode(bytes ->
            {
                try
                {
                    stream.write(bytes);
                }
                catch (Throwable error)
                {
                    errorHandler.call(error);
                }
            });
        }, errorHandler);
    }
}
