package jena.opengl;

import javax.swing.JFrame;

import com.jogamp.newt.opengl.GLWindow;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLProfile;

import jena.engine.graphics.GraphicsRectf;
import jena.engine.math.Rectf;
import jena.engine.math.Vector2f;
import jena.environment.EnvironmentVariables;

public class OpenGLWindow extends JFrame
{
    public OpenGLWindow(EnvironmentVariables variables)
    {
        int width = 800;
        int height = 600;
        
        GLProfile.initSingleton();
        GLProfile profile = GLProfile.getDefault();
        GLCapabilities cap = new GLCapabilities(profile);
        GLWindow window = GLWindow.create(cap);
        window.setSize(width, height);

        Vector2f screenSize = a -> a.call(window.getWidth(), window.getHeight());
        Rectf paintRect = new GraphicsRectf(screenSize, a -> a.call(width, height));

        window.addGLEventListener(new OpenGLWindowListener(window, paintRect));

        window.setVisible(true);
    }
}