package jena.opengl.gles;

import jena.engine.io.FileResource;
import jena.opengl.OpenGLShaderSource;
import jena.opengl.OpenGLShaderSourceAcceptor;
import jena.opengl.shader.OpenGLFileShaderSource;
import jena.opengl.shader.OpenGLShaderPrecisionMacro;
import jena.opengl.shader.OpenGLShaderVersionMacro;

public class OpenGLESFileShaderSource implements OpenGLShaderSource 
{
    OpenGLShaderSource source;

    public OpenGLESFileShaderSource(FileResource file)
    {
        source = new OpenGLFileShaderSource(file,
            new OpenGLShaderVersionMacro("300 es"),
            new OpenGLShaderPrecisionMacro());
    }

    @Override
    public void accept(OpenGLShaderSourceAcceptor acceptor)
    {
        source.accept(acceptor);
    }
}