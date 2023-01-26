package jena.opengl;

import com.jogamp.newt.opengl.GLWindow;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GL2ES1;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.util.FPSAnimator;

import jena.engine.common.CallChain;
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
import jena.engine.math.RectfStruct;
import jena.opengl.gles.OpenGLESBufferPrimitiveBuilder;
import jena.opengl.gles.OpenGLESMatrixPipeline;
import jena.opengl.primitive.OpenGLPrimitiveBuilder;
import jena.opengl.texture.OpenGLDiffuseTexture;

public class OpenGLWindowListener implements GLEventListener
{
    FPSAnimator animator;
    GLWindow window;
    Rectf paintArea;
    GraphicsDevicePainter root;
    FrameStartListener frameStart;
    FrameEndListener frameEnd;
    OpenGLPrimitiveBuilder primitives;

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

        GL2ES1 gl = drawable.getGL().getGL2ES1();
        gl.glDisable(GL2.GL_DEPTH_TEST);
        gl.glClearColor(0.5f, 0.5f, 0.5f, 1f);
        gl.glClear(GL2.GL_COLOR_BUFFER_BIT);

        root.paint(new OpenGLDevice(gl, () -> new OpenGLESMatrixPipeline(gl), primitives, paintArea, System.out::println));

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

        OpenGLKeyboard keyboard = new OpenGLKeyboard();
        Player player = new Player(new OpenGLGraphicsResource(drawable.getGLProfile(), System.out::println), keyboard);

        primitives = new OpenGLESBufferPrimitiveBuilder(drawable.getGL().getGL2ES3(), System.out::println);

        frameStart = player;
        frameEnd = () ->
        {
            player.onEndFrame();
            keyboard.onEndFrame();
        };

        window.addKeyListener(keyboard);

        root = new Camera(
            a -> new CallChain<RectfStruct, Rectf>(r -> n -> n.call(0f, 0f, r.width, r.height))
            .<Rectf>join(r -> new RectfStruct(r))
            .close(paintArea).accept(a),
            a -> a.call(200, 100, 100, 255), player);

        animator = new FPSAnimator(window, 60);
        animator.start();
    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int w, int h)
    {
    }
}