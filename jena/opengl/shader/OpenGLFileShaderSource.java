package jena.opengl.shader;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import jena.engine.common.FunctionBox;
import jena.engine.io.TextFileReader;
import jena.engine.io.FileResource;
import jena.opengl.OpenGLShaderSource;

public class OpenGLFileShaderSource implements OpenGLShaderSource
{
    FileResource file;
    OpenGLShaderMacro[] macros;

    public OpenGLFileShaderSource(FileResource file, OpenGLShaderMacro... macros)
    {
        this.file = file;
        this.macros = macros;
    }

    @Override
    public String read()
    {
        List<String> appendStart = new ArrayList<String>();
        List<String> prependStart = new ArrayList<String>();
        OpenGLShaderEditableSource editable = new OpenGLShaderEditableSource()
        {
            @Override
            public void appendStart(String line)
            {
                appendStart.add(line);
            }
            @Override
            public void prependStart(String line)
            {
                prependStart.add(line);   
            }
        };
        for(OpenGLShaderMacro macro : macros) macro.edit(editable);
        String header = String.join("\n", Stream.concat(prependStart.stream(), appendStart.stream()).collect(Collectors.toList()));
        String source = new FunctionBox<String>(resultAcceptor -> new TextFileReader(file).read(lines -> 
        {
            resultAcceptor.call(String.join("\n", lines));
        },
        System.out::println), () -> "").call();
        return String.join("\n", header, source);
    }
}