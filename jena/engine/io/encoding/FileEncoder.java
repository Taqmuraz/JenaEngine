package jena.engine.io.encoding;

import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.util.stream.IntStream;

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
            Charset charset = Charset.forName("US-ASCII");
            OutputStreamWriter writer = new OutputStreamWriter(stream, charset);
            encodable.encode(bytes ->
            {
                char[] text = new char[bytes.length];
                IntStream.range(0, bytes.length).boxed().forEach(i -> text[i] = (char) bytes[i]);
                try
                {
                    writer.write(text);
                }
                catch (Throwable error)
                {
                    errorHandler.call(error);
                }
            });
            try { writer.close(); } catch(Throwable error) { }
        }, errorHandler);
    }
}
