package jena.opengl.gles;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import jena.engine.common.ErrorHandler;
import jena.engine.io.StorageFileResource;
import jena.opengl.OpenGLFunctions;
import jena.opengl.OpenGLPrimitive;
import jena.opengl.OpenGLShader;
import jena.opengl.OpenGLUniformsPrimitive;
import jena.opengl.OpenGLVertexArray;
import jena.opengl.OpenGLVertexBuffer;
import jena.opengl.primitive.OpenGLPrimitiveBuilder;
import jena.opengl.shader.OpenGLStandardShader;

public class OpenGLESBufferPrimitiveBuilder implements OpenGLPrimitiveBuilder
{
    private OpenGLFunctions gl;
    private OpenGLShader shader;

    private OpenGLPrimitive quad;

    public OpenGLESBufferPrimitiveBuilder(OpenGLFunctions gl, ErrorHandler errorHandler)
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

        createQuad();
    }

    private void loadAttributeBuffer(int index, int stride, float[] data, OpenGLVertexBuffer buffer)
    {
        buffer.bind(vbo ->
        {
            vbo.data(data);
            vbo.floatAttribPointer(index, stride);
        });
    }

    @Override
    public OpenGLPrimitive quad()
    {
        return quad;
    }

    private void createQuad()
    {
        OpenGLVertexArray vao = gl.genVertexArray();

        vao.bind(vaoContext ->
        {
            int[] indices = new int[]
            {
                0, 1, 2,
                2, 3, 0
            };

            OpenGLVertexBuffer[] vbos = vaoContext.genBuffers(2, indices);

            float[] positions = new float[]
            {
                    0f, 0f,
                    0f, 1f,
                    1f, 1f,
                    1f, 0f
            };
            float[] uvs = new float[]
            {
                    0f, 1f,
                    0f, 0f,
                    1f, 0f,
                    1f, 1f
            };

            loadAttributeBuffer(0, 2, positions, vbos[0]);
            loadAttributeBuffer(1, 2, uvs, vbos[1]);

            quad = () -> shader.play(() ->
            {
                vao.bind(v -> v.drawTriangles());
            });
        });

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
            0f, 1f,
            0f, 0f,
            1f, 0f,
            1f, 1f
        };

        loadAttributeBuffer(0, 2, positions, vboBuffer.get(1));
        loadAttributeBuffer(1, 2, uvs, vboBuffer.get(2));

        quad = () -> shader.play(() ->
        {
            gl.glDisable(GL.GL_CULL_FACE);
            gl.glBindVertexArray(vaoID);
            gl.glEnableVertexAttribArray(0);
            gl.glEnableVertexAttribArray(1);
            gl.glDrawElements(GL.GL_TRIANGLES, indices.length, GL.GL_UNSIGNED_INT, 0);
        });
    }

    @Override
    public OpenGLPrimitive fromUniforms(OpenGLUniformsPrimitive acceptor)
    {
        return () -> shader.play(acceptor.create(shader)::draw);
    }
}