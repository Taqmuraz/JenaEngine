package jena.opengl;

import com.jogamp.newt.opengl.GLWindow;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.util.FPSAnimator;

import jena.engine.entity.Camera;
import jena.engine.entity.FrameEndListener;
import jena.engine.entity.FrameStartListener;
import jena.engine.entity.human.Player;
import jena.engine.graphics.GraphicsDevicePainter;
import jena.engine.graphics.GraphicsResource;
import jena.engine.graphics.TextureHandle;
import jena.engine.io.FileResource;
import jena.engine.math.Rectf;
import jena.engine.math.RectfStruct;

public class OpenGLWindowListener implements GLEventListener
{
    FPSAnimator animator;
    GLWindow window;
    Rectf paintArea;
    GraphicsDevicePainter root;
    FrameStartListener frameStart;
    FrameEndListener frameEnd;

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
        OpenGLKeyboard keyboard = new OpenGLKeyboard();
        Player player = new Player(new OpenGLGraphicsResource(), keyboard);

        frameStart = player;
        frameEnd = () ->
        {
            player.onEndFrame();
            keyboard.onEndFrame();
        };

        window.addGLEventListener(this);
        window.addKeyListener(keyboard);
        animator = new FPSAnimator(window, 60);
        animator.start();

        root = new Camera(a ->
        {
            RectfStruct area = new RectfStruct(paintArea);
            a.call(0f, 0f, area.width, area.height);
        }, a -> a.call(200, 100, 100, 255), player);
    }

    @Override
    public void display(GLAutoDrawable drawable)
    {
        frameStart.onStartFrame();

        GL2 gl = drawable.getGL().getGL2();
        gl.glDisable(GL2.GL_DEPTH_TEST);
        gl.glClearColor(0.5f, 0.5f, 0.5f, 1f);
        gl.glClear(GL2.GL_COLOR_BUFFER_BIT);

        root.paint(new OpenGLDevice(gl, paintArea));

        frameEnd.onEndFrame();
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