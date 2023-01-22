package jena.opengl.shader;

import jena.engine.common.Action;
import jena.opengl.OpenGLShader;
import jena.opengl.OpenGLShaderAttributeCollection;
import jena.opengl.OpenGLShaderEnvironment;
import jena.opengl.OpenGLShaderProgram;
import jena.opengl.OpenGLShaderSource;

public class OpenGLStandardShader implements OpenGLShader
{
    private OpenGLShaderProgram program;

    public OpenGLStandardShader(OpenGLShaderEnvironment environment, OpenGLShaderSource vertex, OpenGLShaderSource fragment, OpenGLShaderAttributeCollection attributes)
    {
        program = environment.createProgram(vertex, fragment, attributes);
    }

    @Override
    public void play(Action action)
    {
        program.execute(action);
    }
}