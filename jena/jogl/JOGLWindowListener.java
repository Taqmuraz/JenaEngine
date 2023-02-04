package jena.jogl;

import com.jogamp.newt.opengl.GLWindow;
import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GL2ES1;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.util.FPSAnimator;

import jena.engine.common.Acceptable;
import jena.engine.common.ErrorHandler;
import jena.engine.entity.Camera;
import jena.engine.entity.FrameEndListener;
import jena.engine.entity.FrameStartListener;
import jena.engine.entity.human.Player;
import jena.engine.graphics.GraphicsDevicePainter;
import jena.engine.graphics.GraphicsResource;
import jena.engine.graphics.PostponedGraphicsDevice;
import jena.engine.graphics.TextureHandle;
import jena.engine.io.FileResource;
import jena.engine.math.Rectf;
import jena.environment.EnvironmentVariables;
import jena.environment.variable.IntegerVariable;
import jena.opengl.OpenGLDevice;
import jena.opengl.gles.OpenGLESBufferPrimitiveBuilder;
import jena.opengl.gles.OpenGLESMatrixPipeline;
import jena.opengl.primitive.OpenGLPrimitiveBuilder;

public class JOGLWindowListener implements GLEventListener
{
    FPSAnimator animator;
    GLWindow window;
    Rectf paintArea;
    GraphicsDevicePainter root;
    FrameStartListener frameStart;
    FrameEndListener frameEnd;
    OpenGLPrimitiveBuilder primitives;
    EnvironmentVariables variables;

    class OpenGLGraphicsResource implements GraphicsResource
    {
        GL gl;
        GLProfile profile;
        ErrorHandler errorHandler;

        public OpenGLGraphicsResource(GL gl, GLProfile profile, ErrorHandler errorHandler)
        {
            this.profile = profile;
            this.errorHandler = errorHandler;
        }

        @Override
        public TextureHandle loadTexture(FileResource file)
        {
            return new JOGLTextureFunctions.JOGLTexture(profile, file, System.out::println);
        }
    }

    public JOGLWindowListener(GLWindow window, Rectf paintArea, EnvironmentVariables variables)
    {
        this.paintArea = paintArea;
        this.window = window;
        this.variables = variables;
    }

    @Override
    public void display(GLAutoDrawable drawable)
    {
        frameStart.onStartFrame();

        GL2ES1 gl = drawable.getGL().getGL2ES1();
        gl.glDisable(GL2.GL_DEPTH_TEST);
        gl.glClearColor(0f, 0f, 0f, 1f);
        gl.glClear(GL2.GL_COLOR_BUFFER_BIT);

        root.paint(
            new OpenGLDevice((new JOGLTextureFunctions(drawable.getGL())),
            () -> new OpenGLESMatrixPipeline(new JOGLMatrixFunctions(gl)),
            primitives, paintArea, System.out::println));

        frameEnd.onEndFrame();
    }

    @Override
    public void dispose(GLAutoDrawable drawable)
    {
        System.out.println("disposed");
        animator.stop();
    }

    @Override
    public void init(GLAutoDrawable drawable)
    {
        System.out.println("initialized");

        JOGLKeyboard keyboard = new JOGLKeyboard();
        Player player = new Player(new OpenGLGraphicsResource(drawable.getGL(), drawable.getGLProfile(), System.out::println), keyboard);

        primitives = new OpenGLESBufferPrimitiveBuilder(
            new JOGLBufferFunctions(drawable.getGL().getGL2ES3()),
            new JOGLShaderEnvironment(drawable.getGL().getGL2ES3(), System.out::println), System.out::println);

        frameStart = player;
        frameEnd = () ->
        {
            player.onEndFrame();
            keyboard.onEndFrame();
        };

        window.addKeyListener(keyboard);

        root = new PostponedGraphicsDevice(new Camera(
            a -> paintArea.accept((x, y, w, h) -> a.call(0f, 0f, w, h)),
            a -> a.call(200, 100, 100, 255), player));

        Acceptable<Integer> fps = a -> variables.<IntegerVariable>findVariable("fps", v -> a.call(v.value()), () -> a.call(60));
        fps.accept(f -> animator = new FPSAnimator(window, f));
        animator.start();
    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int w, int h)
    {
    }
}