package jena.engine.io.encoding;

import jena.engine.common.ErrorHandler;
import jena.engine.io.ByteArrayOutput;
import jena.engine.io.MinCount;
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
        input.read(flow -> decodable.decode(length ->
        {
            byte[] buffer = new byte[length];
            flow.read(new MinCount(length), new ByteArrayOutput(buffer));
            return buffer;
        }),
        errorHandler);
    }
}