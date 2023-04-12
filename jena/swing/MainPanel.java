package jena.swing;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.JPanel;

import jena.engine.common.CachedIterable;
import jena.engine.common.ErrorHandler;
import jena.engine.common.MapIterable;
import jena.engine.entity.Camera;
import jena.engine.entity.FrameEndListener;
import jena.engine.entity.FrameStartListener;
import jena.engine.entity.KeyboardController;
import jena.engine.game.Game;
import jena.engine.graphics.GraphicsResource;
import jena.engine.graphics.GraphicsBrushPainter;
import jena.engine.graphics.GraphicsDevice;
import jena.engine.graphics.GraphicsDevicePainter;
import jena.engine.graphics.GraphicsDrawing;
import jena.engine.graphics.TextureHandle;
import jena.engine.io.FileStorage;
import jena.engine.io.Storage;
import jena.engine.io.StorageResource;
import jena.engine.math.Rectf;
import jena.engine.math.Vector2f;
import jena.engine.graphics.GraphicsRectf;
import jena.engine.graphics.CompositeGraphicsDrawing;

public class MainPanel extends JPanel implements GraphicsResource
{
    private ErrorHandler errorHandler;
    private GraphicsDrawing rootDrawing;
    private FrameStartListener frameStartHandler;
    private FrameEndListener frameEndHandler;
    private BufferedImage screenBuffer;
    private Rectf graphicsRect;
    private Vector2f canvasSize; 
    private ThreadLocal<ImageDescriptor> textureDescriptor = new ThreadLocal<>();
    private Graphics2D screen;
    
    public MainPanel(int canvasWidth, int canvasHeight, SwingKeyboard keyboard, SwingMouse mouse, ErrorHandler errorHandler)
    {
        this.errorHandler = errorHandler;
        this.addMouseListener(mouse);
        this.addMouseMotionListener(mouse);
        this.canvasSize = a -> a.call(canvasWidth, canvasHeight);
        Vector2f panelSize = a -> a.call(getWidth(), getHeight());
        this.graphicsRect = new GraphicsRectf(panelSize, a -> a.call(canvasWidth, canvasHeight));
        //Mouse windowMouse = new WindowToGraphicsMouse(mouse, canvasSize, graphicsRect);
        Storage storage = new FileStorage();

        Game game = new Game(this, storage, new KeyboardController(keyboard));
        //ArrayList<ClipGraphicsInspector> inspectors = new ArrayList<ClipGraphicsInspector>();
        GraphicsBrushPainter scene = clip -> game.paint(clip);
            //for(ClipGraphicsInspector inspector : inspectors) inspector.paint(clip);

        int num = 1;
        float dnum = 1f / num;

        List<GraphicsDevicePainter> painters = java.util.stream.IntStream.range(0, num * num).boxed().<GraphicsDevicePainter>map(i ->
        {
            float x = (i % num) * dnum;
            float y = (i / num) * dnum;
            Camera camera = new Camera(
                a -> a.call(x * canvasWidth, y * canvasHeight, canvasWidth * dnum, canvasHeight * dnum),
                new jena.engine.graphics.ColorFloatStruct(0f, 0f, 0f, 1f),
                game.position(),
                scene
            );
            //ClipGraphicsInspector inspector = new ClipGraphicsInspector(game, windowMouse, camera.screenToWorld());
            //inspectors.add(inspector);
            return camera;
        })
        .collect(Collectors.toList());

        //String[] buttons = new String[] { "Играться", "Загрузиться", "Выбираться", "Уходить" };

        /*painters.add(new RootCanvas(a -> a.call(450f, 550f, 300f, 55f * buttons.length + 1), windowMouse, canvas ->
        {
            UserCanvas userCanvas = new MenuCanvas(canvas);
            ValueFloat deltaTime = new DeltaTime(new Time());
            canvas.drawText(a -> deltaTime.accept(time ->  a.call(String.format("fps = %s", String.valueOf((int)(1f / time))))), a -> a.call(0f, 0f, 300f, 50f), a -> a.call(255, 255, 255, 255));
            IntStream.range(0, buttons.length).boxed().forEach(b -> 
            {
                userCanvas.drawButton(a -> a.call(buttons[b]), a -> a.call(0f, (b + 1) * 55f, 300f, 50f), c -> c.call(255, 150, 50, 255), c -> c.call(100, 100, 100, 255), () -> System.out.println(b));
            });
        }));*/

        frameStartHandler = game;
        frameEndHandler = () ->
        {
            keyboard.onEndFrame();
            mouse.onEndFrame();
        };

        GraphicsDevice device = new SwingGraphicsDevice(() -> screen, a -> a.call(textureDescriptor.get()));
        
        rootDrawing =
            new CompositeGraphicsDrawing(
                new CachedIterable<>(
                    new MapIterable<>(
                        painters,
                        p -> p.paint(device))));

        screenBuffer = new BufferedImage(canvasWidth, canvasHeight, BufferedImage.TYPE_INT_ARGB);
    }

    @Override
    public void setSize(int width, int height)
    {
        setBounds(0, 0, width, height);
    }

    public void paint(Graphics g)
    {
        frameStartHandler.onStartFrame();
        screen = screenBuffer.createGraphics();
        rootDrawing.draw();

        int w = getWidth();
        int h = getHeight();

        Graphics2D g2 = (Graphics2D)g;
        g2.setBackground(new Color(0, 0, 0, 255));
        g.clearRect(0, 0, w, h);
        canvasSize.accept((cw, ch) ->
            graphicsRect.accept((rx, ry, rw, rh) ->
                g.drawImage(screenBuffer, (int)rx, (int)ry, (int)(rx + rw), (int)(ry + rh), 0, 0, (int)cw, (int)ch, null)));
        frameEndHandler.onEndFrame();
    }

    @Override
    public void setPreferredSize(Dimension size)
    {

    }
    @Override
    public void setMinimumSize(Dimension size)
    {

    }
    @Override
    public void setMaximumSize(Dimension size)
    {

    }

    @Override
    public TextureHandle loadTexture(StorageResource file) 
    {
        return new FileImageResource(file, textureDescriptor::set, errorHandler);
    }
}
