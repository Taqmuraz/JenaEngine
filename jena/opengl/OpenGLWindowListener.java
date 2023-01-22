package jena.opengl;

import com.jogamp.newt.opengl.GLWindow;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.util.FPSAnimator;

import jena.engine.common.ErrorHandler;
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
        GLProfile profile;
        ErrorHandler errorHandler;

        public OpenGLGraphicsResource(GLProfile profile, ErrorHandler errorHandler)
        {
            this.profile = profile;
            this.errorHandler = errorHandler;
        }

        @Override
        public TextureHandle loadTexture(FileResource file)
        {
            return new OpenGLDiffuseTexture(profile, file, errorHandler);
        }
    }

    public OpenGLWindowListener(GLWindow window, Rectf paintArea)
    {
        this.paintArea = paintArea;
        this.window = window;
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

        OpenGLKeyboard keyboard = new OpenGLKeyboard();
        Player player = new Player(new OpenGLGraphicsResource(drawable.getGLProfile(), System.out::println), keyboard);

        frameStart = player;
        frameEnd = () ->
        {
            player.onEndFrame();
            keyboard.onEndFrame();
        };

        window.addKeyListener(keyboard);

        root = new Camera(a ->
        {
            RectfStruct area = new RectfStruct(paintArea);
            a.call(0f, 0f, area.width, area.height);
        }, a -> a.call(200, 100, 100, 255), player);

        animator = new FPSAnimator(window, 60);
        animator.start();
    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int w, int h)
    {
    }
}