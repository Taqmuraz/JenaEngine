package jena.jogl;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.stream.IntStream;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2ES3;

import jena.engine.common.ActionSingle;
import jena.opengl.OpenGLBufferFunctions;
import jena.opengl.OpenGLVertexArray;
import jena.opengl.OpenGLVertexArrayContext;
import jena.opengl.OpenGLVertexBuffer;
import jena.opengl.OpenGLVertexBufferContext;

public class JOGLBufferFunctions implements OpenGLBufferFunctions
{
    GL2ES3 gl;

    public JOGLBufferFunctions(GL2ES3 gl)
    {
        this.gl = gl;
    }

    @Override
    public OpenGLVertexArray genVertexArray(int attributesCount, int[] elements)
    {
        IntBuffer vaoBuffer = IntBuffer.allocate(1);
        gl.glGenVertexArrays(1, vaoBuffer);
        int vaoID = vaoBuffer.get(0);

        return new OpenGLVertexArray()
        {
            @Override
            public void drawTriangles()
            {
                gl.glDisable(GL.GL_CULL_FACE);
                gl.glBindVertexArray(vaoID);
                for(int i = 0; i < attributesCount; i++) gl.glEnableVertexAttribArray(i);
                gl.glDrawElements(GL.GL_TRIANGLES, elements.length, GL.GL_UNSIGNED_INT, 0);
                gl.glBindVertexArray(0);
            }

            @Override
            public void bind(ActionSingle<OpenGLVertexArrayContext> action)
            {
                gl.glBindVertexArray(vaoID);

                action.call(new OpenGLVertexArrayContext()
                {
                    @Override
                    public OpenGLVertexBuffer[] genBuffers()
                    {
                        IntBuffer vboBuffer = IntBuffer.allocate(attributesCount + 1);
                        gl.glGenBuffers(attributesCount + 1, vboBuffer);

                        gl.glBindBuffer(GL2ES3.GL_ELEMENT_ARRAY_BUFFER, vboBuffer.get(0));
                        gl.glBufferData(GL2ES3.GL_ELEMENT_ARRAY_BUFFER, elements.length * 4, IntBuffer.wrap(elements), GL2ES3.GL_STATIC_DRAW);

                        return IntStream.range(1, attributesCount + 1).boxed().map(i -> vboBuffer.get(i)).map(vbo -> new OpenGLVertexBuffer()
                        {
                            @Override
                            public void bind(ActionSingle<OpenGLVertexBufferContext> action)
                            {
                                gl.glBindBuffer(GL2ES3.GL_ARRAY_BUFFER, vbo);
                                action.call(new OpenGLVertexBufferContext()
                                {
                                    @Override
                                    public void data(float[] data)
                                    {
                                        FloatBuffer buffer = FloatBuffer.wrap(data);
                                        gl.glBufferData(GL2ES3.GL_ARRAY_BUFFER, data.length * 4, buffer, GL2ES3.GL_STATIC_DRAW);
                                    }
                                    @Override
                                    public void floatAttribPointer(int index, int stride)
                                    {
                                        gl.glVertexAttribPointer(index, stride, GL2ES3.GL_FLOAT, false, 0, 0);
                                    }
                                });
                                gl.glBindBuffer(GL2ES3.GL_ARRAY_BUFFER, 0);
                            }
                        }).toArray(OpenGLVertexBuffer[]::new);
                    }
                });
                gl.glBindVertexArray(0);
            }
        };
    }
}