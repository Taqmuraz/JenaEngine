package jena.opengl.shader;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import jena.engine.io.TextFileReader;
import jena.engine.io.FileResource;
import jena.opengl.OpenGLShaderSource;
import jena.opengl.OpenGLShaderSourceAcceptor;

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
    public void accept(OpenGLShaderSourceAcceptor acceptor)
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
        new TextFileReader(file).read(lines -> 
        {
            String source = String.join("\n", lines);
            acceptor.call(String.join("\n", header, source));
        },
        System.out::println);
    }
}