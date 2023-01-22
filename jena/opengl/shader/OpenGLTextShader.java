package jena.opengl.shader;

import jena.engine.common.Action;
import jena.opengl.OpenGLShader;
import jena.opengl.OpenGLShaderEnvironment;
import jena.opengl.OpenGLShaderProgram;
import jena.opengl.OpenGLShaderSource;

public class OpenGLTextShader implements OpenGLShader
{
    private OpenGLShaderProgram program;

    public OpenGLTextShader(OpenGLShaderEnvironment environment, OpenGLShaderSource vertex, OpenGLShaderSource fragment)
    {
        program = environment.createProgram(vertex, fragment);
    }

    @Override
    public void play(Action action)
    {
        program.execute(action);
    }
}