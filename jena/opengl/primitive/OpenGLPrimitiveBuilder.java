package jena.opengl.primitive;

import jena.opengl.OpenGLPrimitive;
import jena.opengl.OpenGLUniformsPrimitive;

public interface OpenGLPrimitiveBuilder
{
    OpenGLPrimitive quad(OpenGLUniformsPrimitive acceptor);
    OpenGLPrimitive rect(OpenGLUniformsPrimitive acceptor);
    OpenGLPrimitive ellipse(OpenGLUniformsPrimitive acceptor);
    OpenGLPrimitive rectContour(OpenGLUniformsPrimitive acceptor);
    OpenGLPrimitive ellipseContour(OpenGLUniformsPrimitive acceptor);
}