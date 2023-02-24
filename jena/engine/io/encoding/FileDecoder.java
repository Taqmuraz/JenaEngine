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
        input.read(flow -> decodable.decode(length ->
        {
            byte[] buffer = new byte[length];
            for(int i = 0; i < length && flow.hasNext(); i++) buffer[i] = flow.next();
            return buffer;
        }),
        errorHandler);
    }
}