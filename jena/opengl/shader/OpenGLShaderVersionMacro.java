package jena.opengl.shader;

public class OpenGLShaderVersionMacro implements OpenGLShaderMacro
{
    String version;

    public OpenGLShaderVersionMacro(String version)
    {
        this.version = version;
    }

    @Override
    public void edit(OpenGLShaderEditableSource source)
    {
        source.prependStart(String.format("#version %s", version));
    }
}