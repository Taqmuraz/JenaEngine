package jena.opengl.shader;

public class OpenGLShaderPrecisionMacro implements OpenGLShaderMacro
{
    @Override
    public void edit(OpenGLShaderEditableSource source)
    {
        source.appendStart("#ifdef GL_ES\nprecision mediump float;\n#endif");
    }
}