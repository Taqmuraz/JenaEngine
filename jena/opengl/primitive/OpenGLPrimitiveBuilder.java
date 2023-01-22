package jena.opengl.primitive;

import jena.opengl.OpenGLPrimitive;
import jena.opengl.OpenGLUniformsPrimitive;

public interface OpenGLPrimitiveBuilder
{
    OpenGLPrimitive quad();
    OpenGLPrimitive fromUniforms(OpenGLUniformsPrimitive acceptor);
}