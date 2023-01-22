package jena.opengl.gles;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2ES3;

import jena.engine.common.ErrorHandler;
import jena.engine.io.StorageFileResource;
import jena.engine.math.Rectf;
import jena.opengl.OpenGLPrimitive;
import jena.opengl.OpenGLShader;
import jena.opengl.primitive.OpenGLPrimitiveBuilder;
import jena.opengl.shader.OpenGLStandardShader;

public class OpenGL2ES3BufferPrimitiveBuilder implements OpenGLPrimitiveBuilder
{
    private GL2ES3 gl;
    private OpenGLShader shader;

    public OpenGL2ES3BufferPrimitiveBuilder(GL2ES3 gl, ErrorHandler errorHandler)
    {
        this.gl = gl;
        shader = new OpenGLStandardShader
        (
            new OpenGLESShaderEnvironment(gl, errorHandler),
            new OpenGLESFileShaderSource(new StorageFileResource("shaders/vertex.glsl")),
            new OpenGLESFileShaderSource(new StorageFileResource("shaders/fragment.glsl")),
            acceptor ->
            {
                acceptor.call(0, "position");
                acceptor.call(1, "texcoord");
            }
        );
    }

    private void loadAttributeBuffer(int index, int stride, float[] data, int vboID)
    {
        gl.glBindBuffer(GL2ES3.GL_ARRAY_BUFFER, vboID);
        FloatBuffer buffer = FloatBuffer.wrap(data);
        gl.glBufferData(GL2ES3.GL_ARRAY_BUFFER, data.length * 4, buffer, GL2ES3.GL_STATIC_DRAW);
        gl.glVertexAttribPointer(index, stride, GL2ES3.GL_FLOAT, false, 0, 0);
        gl.glBindBuffer(GL2ES3.GL_ARRAY_BUFFER, 0);
    }

    @Override
    public OpenGLPrimitive quad(Rectf rect)
    {
        IntBuffer vaoBuffer = IntBuffer.allocate(1);
        gl.glGenVertexArrays(1, vaoBuffer);
        int vaoID = vaoBuffer.get(0);
        gl.glBindVertexArray(vaoID);

        IntBuffer vboBuffer = IntBuffer.allocate(3);
        gl.glGenBuffers(3, vboBuffer);

        int[] indices = new int[]
        {
            0, 1, 2,
            2, 3, 0
        };

        gl.glBindBuffer(GL2ES3.GL_ELEMENT_ARRAY_BUFFER, vboBuffer.get(0));
        gl.glBufferData(GL2ES3.GL_ELEMENT_ARRAY_BUFFER, indices.length * 4, IntBuffer.wrap(indices), GL2ES3.GL_STATIC_DRAW);

        float[] positions = new float[]
        {
            0f, 0f,
            0f, 1f,
            1f, 1f,
            1f, 0f
        };
        float[] uvs = new float[]
        {
            0f, 0f,
            1f, 0f,
            1f, 1f,
            0f, 1f
        };

        loadAttributeBuffer(0, 2, positions, vboBuffer.get(1));
        loadAttributeBuffer(1, 2, uvs, vboBuffer.get(2));

        return () -> shader.play(() ->
        {
            gl.glBindVertexArray(vaoID);
            gl.glEnableVertexAttribArray(0);
            gl.glEnableVertexAttribArray(1);
            gl.glDrawElements(GL.GL_TRIANGLES, indices.length, GL.GL_UNSIGNED_INT, 0);
        });
    }
}