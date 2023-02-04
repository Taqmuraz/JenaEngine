package jena.engine.io.encoding;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.stream.Collectors;

import jena.engine.io.FileStorageResource;
import jena.engine.io.StorageResource;

public class ResourcesEncoder implements Encodable
{
    Map<String, StorageResource> resources;

    public ResourcesEncoder()
    {
        File root = new File("resources");
        try
        {
            resources = Files.find(Paths.get(root.getAbsolutePath()), 1024, (a, b) -> b.isRegularFile()).map(p -> p.toFile()).map(file -> file.getAbsolutePath().replace(root.getAbsolutePath(), "")).collect(Collectors.toMap(file -> file, file -> new FileStorageResource(file)));
        }
        catch(Throwable error)
        {
            System.out.println(error);
        }
    }

    @Override
    public void encode(EncodingStream stream)
    {
        stream.writeInt(resources.size());
        resources.forEach((name, resource) ->
        {
            System.out.println("Resource encoded : " + name);
            resource.read(input ->
            {
                try
                {
                    byte[] bytes = input.readAllBytes();
                    stream.writeText(name);
                    stream.writeByteArray(bytes);
                }
                catch(Throwable error)
                {
                    System.out.println(error);
                }
            },
            System.out::println);
        });
    }
}