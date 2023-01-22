package jena.opengl.primitive;

import jena.engine.math.Rectf;
import jena.opengl.OpenGLPrimitive;

public interface OpenGLPrimitiveBuilder
{
    OpenGLPrimitive quad(Rectf rect);
}