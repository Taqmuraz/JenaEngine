package jena.opengl;

import com.jogamp.newt.opengl.GLWindow;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.util.FPSAnimator;

import jena.engine.entity.Camera;
import jena.engine.entity.human.Player;
import jena.engine.graphics.GraphicsDevicePainter;
import jena.engine.graphics.GraphicsResource;
import jena.engine.graphics.TextureHandle;
import jena.engine.input.Key;
import jena.engine.io.FileResource;
import jena.engine.math.Rectf;

public class OpenGLWindowListener implements GLEventListener
{
    FPSAnimator animator;
    GLWindow window;
    Rectf paintArea;
    GraphicsDevicePainter root;

    class OpenGLGraphicsResource implements GraphicsResource
    {
        @Override
        public TextureHandle loadTexture(FileResource file)
        {
            return new TextureHandle() {};
        }
    }

    public OpenGLWindowListener(GLWindow window, Rectf paintArea)
    {
        this.paintArea = paintArea;
        window.addGLEventListener(this);
        animator = new FPSAnimator(window, 60);
        animator.start();

        root = new Camera(paintArea, a -> a.call(200, 100, 100, 255), new Player(new OpenGLGraphicsResource(), c -> new Key()
        {
            @Override
            public boolean isDown()
            {
                return false;
            }

            @Override
            public boolean isUp()
            {
                return false;
            }

            @Override
            public boolean isHold()
            {
                return false;
            }
        }));
    }

    @Override
    public void display(GLAutoDrawable drawable)
    {
        GL2 gl = drawable.getGL().getGL2();

        gl.glDepthFunc(GL2.GL_ALWAYS);
        //gl.glDisable(GL2.GL_DEPTH_TEST);
        gl.glClearColor(0.5f, 0.5f, 0.5f, 1f);
        gl.glClear(GL2.GL_COLOR_BUFFER_BIT);

        root.paint(new OpenGLDevice(gl, paintArea));
        /*gl.glLoadIdentity();
        gl.glViewport(30, 30, 100, 100);
        gl.glOrthof(0, 800, 600, 0, -1f, 1);

        gl.glClearColor(0.5f, 0.5f, 0.5f, 1f);
        gl.glClear(GL2.GL_DEPTH_BUFFER_BIT | GL.GL_COLOR_BUFFER_BIT);
        gl.glBegin(GL2.GL_QUADS);

        float n = 0;
        float m = 800;

        gl.glColor3f(1f, 1f, 0f);
        gl.glVertex2f(n, n);
        gl.glVertex2f(n, m);
        gl.glVertex2f(m, m);
        gl.glVertex2f(m, n);
        gl.glEnd();
        */
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