package jena.opengl;

import javax.swing.JFrame;

import com.jogamp.newt.opengl.GLWindow;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.util.FPSAnimator;

import jena.engine.graphics.GraphicsRectf;
import jena.engine.math.Rectf;
import jena.engine.math.Vector2f;
import jena.environment.EnvironmentVariables;

public class OpenGLWindow extends JFrame
{
    Vector2f canvasSize;
    Rectf paintRect;

    public OpenGLWindow(EnvironmentVariables variables)
    {
        int width = 800;
        int height = 600;

        GLProfile.initSingleton();
        GLProfile profile = GLProfile.get(GLProfile.GL2);
        GLCapabilities cap = new GLCapabilities(profile);
        GLWindow window = GLWindow.create(cap);
        window.setSize(width, height);
        new OpenGLWindowListener(window);

        canvasSize = a -> a.call(window.getWidth(), window.getHeight());
        paintRect = new GraphicsRectf(canvasSize, a -> a.call(width, height));

        window.setVisible(true);
    }
}