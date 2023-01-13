package jena.swing;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.swing.JPanel;

import jena.engine.common.ErrorHandler;
import jena.engine.entity.Camera;
import jena.engine.entity.FrameEndHandler;
import jena.engine.entity.FrameStartHandler;
import jena.engine.entity.Time;
import jena.engine.entity.human.Player;
import jena.engine.graphics.GraphicsResource;
import jena.engine.graphics.GraphicsDevice;
import jena.engine.graphics.GraphicsDevicePainter;
import jena.engine.graphics.TextureHandle;
import jena.engine.input.Mouse;
import jena.engine.input.WindowToGraphicsMouse;
import jena.engine.io.FileResource;
import jena.engine.math.Rectf;
import jena.engine.math.Vector2f;
import jena.engine.ui.MenuCanvas;
import jena.engine.ui.RootCanvas;
import jena.engine.ui.UserCanvas;
import jena.engine.graphics.GraphicsRectf;

public class MainPanel extends JPanel implements GraphicsResource
{
	private ErrorHandler errorHandler;
	private GraphicsDevicePainter rootPainter;
	private FrameStartHandler frameStartHandler;
	private FrameEndHandler frameEndHandler;
	private BufferedImage screenBuffer;
	private Rectf graphicsRect;
	private Vector2f canvasSize;
	
	public MainPanel(int canvasWidth, int canvasHeight, SwingKeyboard keyboard, SwingMouse mouse, ErrorHandler errorHandler)
	{
		this.errorHandler = errorHandler;
		this.addMouseListener(mouse);
		this.addMouseMotionListener(mouse);
		this.canvasSize = a -> a.call(canvasWidth, canvasHeight);
		Vector2f panelSize = a -> a.call(getWidth(), getHeight());
		this.graphicsRect = new GraphicsRectf(panelSize, a -> a.call(canvasWidth, canvasHeight));
		Mouse windowMouse = new WindowToGraphicsMouse(mouse, canvasSize, graphicsRect);
		
		Player player = new Player(this, keyboard);

		int num = 1;
		float dnum = 1f / num;

		List<GraphicsDevicePainter> painters = java.util.stream.IntStream.range(0, num * num).boxed().map(i ->
		{
			float x = (i % num) * dnum;
			float y = (i / num) * dnum;
			return (GraphicsDevicePainter)new Camera(a -> a.call(x * canvasWidth, y * canvasHeight, canvasWidth * dnum, canvasHeight * dnum), new jena.engine.graphics.ColorFloatStruct(x * 0.25f + 0.25f, y * 0.25f + 0.25f, 0.5f, 1f), player);
		})
		.collect(Collectors.toList());

		String[] buttons = new String[] { "Играться", "Загрузиться", "Выбираться", "Уходить" };

		painters.add(new RootCanvas(a -> a.call(450f, 550f, 300f, 55f * buttons.length), windowMouse, canvas ->
		{
			UserCanvas userCanvas = new MenuCanvas(canvas);
			IntStream.range(0, buttons.length).boxed().forEach(b -> 
			{
				userCanvas.drawButton(() -> buttons[b], a -> a.call(0f, b * 55f, 300f, 50f), c -> c.call(255, 150, 50, 255), c -> c.call(100, 100, 100, 255), () -> System.out.println(b));
			});
		}));

		frameStartHandler = player;
		frameEndHandler = () ->
		{
			keyboard.updateState();
			mouse.updateState();
		};
		GraphicsDevicePainter painter = device ->
		{
			for(GraphicsDevicePainter p : painters)
			{
				p.paint(device);
			}
		};
		var bakedDevice = new PostponedGraphicsDevice();
		painter.paint(bakedDevice);
		rootPainter = bakedDevice;

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
		Graphics2D screen = screenBuffer.createGraphics();
		GraphicsDevice device = new SwingGraphicsDevice(screen);
		rootPainter.paint(device);

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
	public TextureHandle loadTexture(FileResource file) 
	{
		return new FileImageResource(file, errorHandler);
	}
}
