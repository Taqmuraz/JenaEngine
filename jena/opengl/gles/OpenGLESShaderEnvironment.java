package jena.opengl.gles;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.jogamp.opengl.GL2ES3;

import jena.engine.common.Action;
import jena.engine.common.ErrorHandler;
import jena.engine.common.FunctionThrowsHandler;
import jena.engine.math.Matrix3f;
import jena.engine.math.Matrix3fElements;
import jena.opengl.OpenGLShaderAttributeCollection;
import jena.opengl.OpenGLShaderEnvironment;
import jena.opengl.OpenGLShaderProgram;
import jena.opengl.OpenGLShaderSource;

public class OpenGLESShaderEnvironment implements OpenGLShaderEnvironment
{
    GL2ES3 gl;
    ErrorHandler errorHandler;

    public OpenGLESShaderEnvironment(GL2ES3 gl, ErrorHandler errorHandler)
    {
        this.gl = gl;
        this.errorHandler = errorHandler;
    }

    private int loadShaderSubprogram(String source, int type, ErrorHandler errorHandler)
    {
        int shaderID = gl.glCreateShader(type);
        gl.glShaderSource(shaderID, 1, new String[] { source }, IntBuffer.allocate(1));
        gl.glCompileShader(shaderID);

        IntBuffer compileStatus = IntBuffer.allocate(1);
        gl.glGetShaderiv(shaderID, GL2ES3.GL_COMPILE_STATUS, compileStatus);
        if (compileStatus.get(0) == 0)
        {
            IntBuffer length = IntBuffer.allocate(1);
            int bufferSize = 2048;
            ByteBuffer message = ByteBuffer.allocate(bufferSize);
            gl.glGetShaderInfoLog(shaderID, bufferSize, length, message);

            String[] lines = source.split("\n");
            errorHandler.call(new Exception(String.format("Shader compile error\n%s\n%s", new FunctionThrowsHandler<String, UnsupportedEncodingException>(() -> new String(message.array(), "UTF-8"), error -> "encoding error").call(),
                String.join("\n", IntStream.range(0, lines.length).boxed().map(i -> String.format("%d\t%s", i, lines[i])).collect(Collectors.toList())))));
        }
        return shaderID;
    }

    @Override
    public OpenGLShaderProgram createProgram(OpenGLShaderSource vertex, OpenGLShaderSource fragment, OpenGLShaderAttributeCollection attributes)
    {
        int program = gl.glCreateProgram();
        gl.glAttachShader(program, loadShaderSubprogram(vertex.read(), GL2ES3.GL_VERTEX_SHADER, errorHandler));
        gl.glAttachShader(program, loadShaderSubprogram(fragment.read(), GL2ES3.GL_FRAGMENT_SHADER, errorHandler));

        attributes.acceptAll((index, name) ->
        {
            gl.glBindAttribLocation(program, index, name);
        });

        gl.glLinkProgram(program);
        gl.glValidateProgram(program);

        return new OpenGLShaderProgram()
        {

            @Override
            public void execute(Action action)
            {
                gl.glUseProgram(program);
                action.call();
                gl.glUseProgram(0);
            }

            @Override
            public void loadUniformMatrix(String name, Matrix3f matrix)
            {
                Matrix3fElements elements = matrix.elements();
                float[] buffer = new float[9];
                for(int i = 0; i < 9; i++) buffer[i] = elements.at(i);
                gl.glUniformMatrix3fv(gl.glGetUniformLocation(program, name), 1, false, buffer, 0);
            }
        };
    }
}