package jena.opengl;

public interface OpenGLShaderEnvironment
{
    OpenGLShaderProgram createProgram(OpenGLShaderSource vertex, OpenGLShaderSource fragment, OpenGLShaderAttributeCollection attributes);
}