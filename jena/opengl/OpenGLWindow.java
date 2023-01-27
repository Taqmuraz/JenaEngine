package jena.opengl;

import javax.swing.JFrame;

import com.jogamp.newt.opengl.GLWindow;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLContext;
import com.jogamp.opengl.GLProfile;

import jena.engine.common.Box;
import jena.engine.graphics.GraphicsRectf;
import jena.engine.math.Rectf;
import jena.engine.math.Vector2f;
import jena.environment.EnvironmentVariables;
import jena.environment.variable.DimensionVariable;

public class OpenGLWindow extends JFrame
{
    public OpenGLWindow(EnvironmentVariables variables)
    {
        Box<Integer> width = new Box<Integer>(() -> 800);
        Box<Integer> height = new Box<Integer>(() -> 600);

        variables.<DimensionVariable>findVariable("resolution", d -> d.accept((w, h) ->
        {
            width.write(w);
            height.write(h);
        }), () ->
        {
            java.awt.Dimension size = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
            width.write(size.width);
            height.write(size.height);
        });
        
        GLProfile.initSingleton();
        GLProfile profile = GLProfile.get(GLProfile.GL3bc);
        GLCapabilities cap = new GLCapabilities(profile);
        GLWindow window = GLWindow.create(cap);
        window.setContextCreationFlags(GLContext.CTX_OPTION_DEBUG);
        window.setSize(width.read(), height.read());

        variables.findVariable("fullscreen", flag -> window.setFullscreen(true), () -> {});

        Vector2f screenSize = a -> a.call(window.getWidth(), window.getHeight());
        Rectf paintRect = new GraphicsRectf(screenSize, a -> a.call(width.read(), height.read()));

        window.addGLEventListener(new OpenGLWindowListener(window, paintRect, variables));

        window.setVisible(true);
    }
}