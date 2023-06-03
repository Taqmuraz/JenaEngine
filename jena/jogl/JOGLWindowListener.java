package jena.jogl;

import com.jogamp.newt.opengl.GLWindow;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GL2ES1;
import com.jogamp.opengl.GL2ES3;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.util.FPSAnimator;

import jena.engine.common.Acceptable;
import jena.engine.common.ErrorHandler;
import jena.engine.graphics.GraphicsDevicePainter;
import jena.engine.graphics.GraphicsDrawing;
import jena.engine.graphics.GraphicsResource;
import jena.engine.graphics.TextureHandle;
import jena.engine.io.FileStorageResource;
import jena.engine.io.Storage;
import jena.engine.io.StorageResource;
import jena.engine.io.encoding.ResourcesDecoder;
import jena.engine.io.encoding.FileDecoder;
import jena.engine.math.Matrix3fStack;
import jena.engine.math.Rectf;
import jena.environment.EnvironmentVariables;
import jena.environment.variable.IntegerVariable;
import jena.game.Camera;
import jena.game.FrameEndListener;
import jena.game.FrameStartListener;
import jena.game.Game;
import jena.game.KeyboardController;
import jena.opengl.OpenGLDevice;
import jena.opengl.gles.OpenGLESBufferPrimitiveBuilder;
import jena.opengl.primitive.OpenGLPrimitiveBuilder;

public class JOGLWindowListener implements GLEventListener
{
    FPSAnimator animator;
    GLWindow window;
    Rectf paintArea;
    GraphicsDrawing root;
    FrameStartListener frameStart;
    FrameEndListener frameEnd;
    OpenGLPrimitiveBuilder primitives;
    EnvironmentVariables variables;
    GL2ES3 activeGl_ES3;
    GL2ES1 activeGl_ES1;
    JOGL_ES3_Provider es3;
    JOGL_ES1_Provider es1;

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
        public TextureHandle loadTexture(StorageResource file)
        {
            return new JOGLTextureFunctions(es3).new JOGLTexture(profile, file, System.out::println);
        }
    }

    public JOGLWindowListener(GLWindow window, Rectf paintArea, EnvironmentVariables variables)
    {
        this.paintArea = paintArea;
        this.window = window;
        this.variables = variables;

        es3 = () -> activeGl_ES3;
        es1 = () -> activeGl_ES1;
    }

    @Override
    public void display(GLAutoDrawable drawable)
    {
        frameStart.onStartFrame();

        activeGl_ES3 = drawable.getGL().getGL2ES3();
        activeGl_ES1 = drawable.getGL().getGL2ES1();

        activeGl_ES3.glDisable(GL2.GL_DEPTH_TEST);
        activeGl_ES3.glClearColor(0f, 0f, 0f, 1f);
        activeGl_ES3.glClear(GL2.GL_COLOR_BUFFER_BIT);

        root.draw();

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

        activeGl_ES3 = drawable.getGL().getGL2ES3();
        activeGl_ES1 = drawable.getGL().getGL2ES1();

        ResourcesDecoder decoder = new ResourcesDecoder();
        new FileDecoder(new FileStorageResource("../resources.resource")).decode(decoder, System.out::println);
        
        Storage storage = decoder.storage();
        JOGLKeyboard keyboard = new JOGLKeyboard();
        Game player = new Game(new OpenGLGraphicsResource(drawable.getGLProfile(), System.out::println), storage, new KeyboardController(keyboard));

        primitives = new OpenGLESBufferPrimitiveBuilder(
            new JOGLBufferFunctions(es3),
            new JOGLShaderEnvironment(es3, System.out::println), storage, System.out::println);

        frameStart = player;
        frameEnd = () ->
        {
            player.onEndFrame();
            keyboard.onEndFrame();
        };

        window.addKeyListener(keyboard);

        GraphicsDevicePainter rootPainter = new Camera(
            a -> paintArea.accept((x, y, w, h) -> a.call(0f, 0f, w, h)),
            a -> a.call(200, 100, 100, 255), player.position(), player);

        root = rootPainter.paint(
            new OpenGLDevice(new JOGLTextureFunctions(es3),
            new JOGLMatrixFunctions(es1),
            Matrix3fStack::new,
            primitives, paintArea, System.out::println));

        Acceptable<Integer> fps = a -> variables.<IntegerVariable>findVariable("fps", v -> a.call(v.value()), () -> a.call(60));
        fps.accept(f -> animator = new FPSAnimator(window, f));
        animator.start();
    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int w, int h)
    {
    }
}