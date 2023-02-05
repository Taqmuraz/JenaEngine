package jena.opengl.gles;

import jena.engine.common.ErrorHandler;
import jena.engine.io.Storage;
import jena.opengl.OpenGLBufferFunctions;
import jena.opengl.OpenGLPrimitive;
import jena.opengl.OpenGLShader;
import jena.opengl.OpenGLShaderAttributeCollection;
import jena.opengl.OpenGLShaderEnvironment;
import jena.opengl.OpenGLUniformsPrimitive;
import jena.opengl.OpenGLVertexArray;
import jena.opengl.OpenGLVertexBuffer;
import jena.opengl.primitive.OpenGLPrimitiveBuilder;
import jena.opengl.shader.OpenGLStandardShader;

public class OpenGLESBufferPrimitiveBuilder implements OpenGLPrimitiveBuilder
{
    private OpenGLBufferFunctions gl;
    private OpenGLShader diffuseShader;
    private OpenGLShader rectContourShader;
    private OpenGLShader ellipseContourShader;
    private OpenGLShader rectShader;
    private OpenGLShader ellipseShader;

    private OpenGLPrimitive quad;

    public OpenGLESBufferPrimitiveBuilder(OpenGLBufferFunctions gl, OpenGLShaderEnvironment shaderEnvironment, Storage storage, ErrorHandler errorHandler)
    {
        this.gl = gl;

        OpenGLShaderAttributeCollection attributes = acceptor ->
        {
            acceptor.call(0, "position");
            acceptor.call(1, "texcoord");
        };

        diffuseShader = new OpenGLStandardShader
        (
            shaderEnvironment,
            new OpenGLESFileShaderSource(storage.open("shaders/vertex.glsl")),
            new OpenGLESFileShaderSource(storage.open("shaders/fragment.glsl")),
            attributes
        );

        rectContourShader = new OpenGLStandardShader
        (
            shaderEnvironment,
            new OpenGLESFileShaderSource(storage.open("shaders/drawVertex.glsl")),
            new OpenGLESFileShaderSource(storage.open("shaders/drawRectFragment.glsl")),
            attributes
        );

        ellipseContourShader = new OpenGLStandardShader
        (
            shaderEnvironment,
            new OpenGLESFileShaderSource(storage.open("shaders/drawVertex.glsl")),
            new OpenGLESFileShaderSource(storage.open("shaders/drawEllipseFragment.glsl")),
            attributes
        );

        rectShader = new OpenGLStandardShader
        (
            shaderEnvironment,
            new OpenGLESFileShaderSource(storage.open("shaders/drawVertex.glsl")),
            new OpenGLESFileShaderSource(storage.open("shaders/fillRectFragment.glsl")),
            attributes
        );

        ellipseShader = new OpenGLStandardShader
        (
            shaderEnvironment,
            new OpenGLESFileShaderSource(storage.open("shaders/drawVertex.glsl")),
            new OpenGLESFileShaderSource(storage.open("shaders/fillEllipseFragment.glsl")),
            attributes
        );

        createPrimitives();
    }

    private void loadAttributeBuffer(int index, int stride, float[] data, OpenGLVertexBuffer buffer)
    {
        buffer.bind(vbo ->
        {
            vbo.data(data);
            vbo.floatAttribPointer(index, stride);
        });
    }

    private OpenGLPrimitive create(OpenGLUniformsPrimitive acceptor, OpenGLPrimitive primitive, OpenGLShader shader)
    {
        return () -> shader.play(acceptor.create(primitive, shader)::draw);
    }

    @Override
    public OpenGLPrimitive quad(OpenGLUniformsPrimitive acceptor)
    {
        return create(acceptor, quad, diffuseShader);
    }
    @Override
    public OpenGLPrimitive rect(OpenGLUniformsPrimitive acceptor)
    {
        return create(acceptor, quad, rectShader);
    }
    @Override
    public OpenGLPrimitive ellipse(OpenGLUniformsPrimitive acceptor)
    {
        return create(acceptor, quad, ellipseShader);
    }
    @Override
    public OpenGLPrimitive rectContour(OpenGLUniformsPrimitive acceptor)
    {
        return create(acceptor, quad, rectContourShader);
    }
    @Override
    public OpenGLPrimitive ellipseContour(OpenGLUniformsPrimitive acceptor)
    {
        return create(acceptor, quad, ellipseContourShader);
    }

    private void createPrimitives()
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

            quad = vao::drawTriangles;
        });
    }
}