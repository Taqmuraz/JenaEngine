package jena.engine.io;

public class FileStorage implements Storage
{
    @Override
    public StorageResource open(String path)
    {
        return new FileStorageResource(path);
    }
}