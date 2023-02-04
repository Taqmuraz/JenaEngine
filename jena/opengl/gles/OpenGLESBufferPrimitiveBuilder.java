package jena.opengl.gles;

import jena.engine.common.ErrorHandler;
import jena.engine.io.StorageFileResource;
import jena.opengl.OpenGLBufferFunctions;
import jena.opengl.OpenGLPrimitive;
import jena.opengl.OpenGLShader;
import jena.opengl.OpenGLShaderEnvironment;
import jena.opengl.OpenGLUniformsPrimitive;
import jena.opengl.OpenGLVertexArray;
import jena.opengl.OpenGLVertexBuffer;
import jena.opengl.primitive.OpenGLPrimitiveBuilder;
import jena.opengl.shader.OpenGLStandardShader;

public class OpenGLESBufferPrimitiveBuilder implements OpenGLPrimitiveBuilder
{
    private OpenGLBufferFunctions gl;
    private OpenGLShader shader;

    private OpenGLPrimitive quad;

    public OpenGLESBufferPrimitiveBuilder(OpenGLBufferFunctions gl, OpenGLShaderEnvironment shaderEnvironment, ErrorHandler errorHandler)
    {
        this.gl = gl;
        shader = new OpenGLStandardShader
        (
            shaderEnvironment,
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
        int[] indices = new int[]
        {
                0, 1, 2,
                2, 3, 0
        };
        OpenGLVertexArray vao = gl.genVertexArray(2, indices);

        vao.bind(vaoContext ->
        {
            OpenGLVertexBuffer[] vbos = vaoContext.genBuffers();

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

            quad = () -> shader.play(vao::drawTriangles);
        });
    }

    @Override
    public OpenGLPrimitive fromUniforms(OpenGLUniformsPrimitive acceptor)
    {
        return () -> shader.play(acceptor.create(shader)::draw);
    }
}