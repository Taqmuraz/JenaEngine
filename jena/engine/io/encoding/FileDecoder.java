package jena.engine.io.encoding;

import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.stream.IntStream;

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
            InputStreamReader reader = new InputStreamReader(stream, Charset.forName("US-ASCII"));
            decodable.decode(length ->
            {
                char[] buffer = new char[length];
                byte[] bytes = new byte[length];
                try
                {
                    reader.read(buffer);
                    IntStream.range(0, length).boxed().forEach(i -> bytes[i] = (byte)buffer[i]);
                }
                catch(Throwable error)
                {
                    errorHandler.call(error);
                }
                return bytes;
            });
            try { reader.close(); } catch(Throwable error) { }
        },
        errorHandler);
    }
}