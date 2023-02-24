package jena.engine.io.encoding;

import jena.engine.common.ErrorHandler;
import jena.engine.io.ByteArrayInput;
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
        output.write(flow -> encodable.encode(bytes ->
        {
            flow.write(new ByteArrayInput(bytes, 0, bytes.length));
        }), errorHandler);
    }
}
