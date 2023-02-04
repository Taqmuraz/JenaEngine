package jena.engine.io.encoding;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

import jena.engine.io.StorageResource;

public class ResourcesDecoder implements Decodable
{
    Map<String, StorageResource> resources;
    public ResourcesDecoder()
    {
        resources = new HashMap<String, StorageResource>();
    }
    @Override
    public void decode(DecodingStream stream)
    {
        int count = stream.readInt();
        IntStream.range(0, count).forEach(i ->
        {
            String name = stream.readText();
            byte[] bytes = stream.readByteArray();
            resources.put(name, new VirtualStorageResource(bytes));
        });
        resources.entrySet().stream().forEach(name -> System.out.println("Resource decoded : " + name));
    }
}