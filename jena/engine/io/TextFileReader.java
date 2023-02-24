package jena.engine.io;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import jena.engine.common.ActionSingle;
import jena.engine.common.ErrorHandler;

public class TextFileReader
{
    private StorageResource file;
    public TextFileReader(StorageResource file)
    {
        this.file = file;
    }
    public void read(ActionSingle<Iterable<String>> scannerAcceptor, ErrorHandler errorHandler)
    {
        file.read(flow ->
        {
            Scanner scanner = new Scanner(new InputStreamFromFlow(flow));
            List<String> lines = new ArrayList<String>();
            while(scanner.hasNextLine())
            {
                lines.add(scanner.nextLine());
            }
            scanner.close();
            scannerAcceptor.call(lines);
        }, errorHandler);
    }
}