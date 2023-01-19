package jena.opengl;

import com.jogamp.newt.opengl.GLWindow;
import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.util.FPSAnimator;

import jena.engine.entity.Time;

public class OpenGLWindowListener implements GLEventListener
{
    FPSAnimator animator;
    GLWindow window;

    public OpenGLWindowListener(GLWindow window)
    {
        window.addGLEventListener(this);
        animator = new FPSAnimator(window, 60);
        animator.start();
    }

    @Override
    public void display(GLAutoDrawable drawable)
    {
        GL2 gl = drawable.getGL().getGL2();

        gl.glLoadIdentity();
        gl.glOrthof(0, 800, 600, 0, -1f, 1);

        gl.glClearColor(0.5f, 0.5f, 0.5f, 1f);
        gl.glClear(GL2.GL_DEPTH_BUFFER_BIT | GL.GL_COLOR_BUFFER_BIT);
        gl.glBegin(GL2.GL_QUADS);
        gl.glColor3f(1f, 1f, 0f);
        gl.glVertex2f(20f, 20f);
        gl.glVertex2f(20f, 50f);
        gl.glVertex2f(50f, 50f);
        gl.glVertex2f(50f, 20f);
        gl.glEnd();
    }

    @Override
    public void dispose(GLAutoDrawable drawable)
    {
        System.out.println("stop");
        animator.stop();
    }

    @Override
    public void init(GLAutoDrawable drawable)
    {
        System.out.println("init");
    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int w, int h)
    {
    }
}