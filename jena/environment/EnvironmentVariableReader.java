package jena.environment;
import java.util.Iterator;

public interface EnvironmentVariableReader
{
    boolean isKey(String key);
    EnvironmentVariable read(Iterator<String> iterator);
}