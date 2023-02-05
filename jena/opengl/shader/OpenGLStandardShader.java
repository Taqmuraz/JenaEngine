package jena.opengl.shader;

import jena.engine.common.Action;
import jena.engine.graphics.Color;
import jena.engine.math.Matrix3f;
import jena.engine.math.Rectf;
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

    @Override
    public void loadUniformMatrix(String name, Matrix3f matrix)
    {
        program.loadUniformMatrix(name, matrix);
    }

    @Override
    public void loadUniformRect(String name, Rectf rect)
    {
        program.loadUniformRect(name, rect);
    }

    @Override
    public void loadUniformColor(String name, Color color)
    {
        program.loadUniformColor(name, color);
    }
}