package jena.jogl;

import javax.swing.JFrame;

import com.jogamp.newt.opengl.GLWindow;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLContext;
import com.jogamp.opengl.GLProfile;

import jena.engine.graphics.GraphicsRectf;
import jena.engine.math.Dimension;
import jena.engine.math.DimensionStruct;
import jena.engine.math.Rectf;
import jena.engine.math.Vector2f;
import jena.environment.EnvironmentVariables;
import jena.environment.variable.DimensionVariable;

public class JOGLWindow extends JFrame
{
    public JOGLWindow(EnvironmentVariables variables)
    {
        Dimension resolution = new DimensionStruct(acceptor -> variables.<DimensionVariable>findVariable("resolution", d ->
        {
            d.accept((w, h) -> acceptor.call(w, h));
        },
        () ->
        {
            java.awt.Dimension size = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
            acceptor.call(size.width, size.height);
        }));
        
        GLProfile.initSingleton();
        GLProfile profile = GLProfile.get(GLProfile.GL3bc);
        GLCapabilities cap = new GLCapabilities(profile);
        GLWindow window = GLWindow.create(cap);
        window.setContextCreationFlags(GLContext.CTX_OPTION_DEBUG);
        resolution.accept(window::setSize);

        variables.findVariable("fullscreen", flag -> window.setFullscreen(true), () -> {});

        Vector2f screenSize = a -> a.call(window.getWidth(), window.getHeight());
        Rectf paintRect = new GraphicsRectf(screenSize, a -> resolution.accept(a::call));

        window.addGLEventListener(new JOGLWindowListener(window, paintRect, variables));

        window.setVisible(true);
    }
}