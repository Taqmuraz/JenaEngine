package jena.swing;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.JPanel;

import jena.engine.common.ErrorHandler;
import jena.engine.entity.Camera;
import jena.engine.entity.FrameEndHandler;
import jena.engine.entity.FrameStartHandler;
import jena.engine.entity.human.Player;
import jena.engine.graphics.GraphicsResource;
import jena.engine.graphics.GraphicsDevice;
import jena.engine.graphics.GraphicsDevicePainter;
import jena.engine.graphics.TextureHandle;
import jena.engine.io.FileResource;
import jena.engine.ui.MenuCanvas;
import jena.engine.ui.RootCanvas;
import jena.engine.ui.UserCanvas;

public class MainPanel extends JPanel implements GraphicsResource
{
	private ErrorHandler errorHandler;
	private GraphicsDevicePainter rootPainter;
	private FrameStartHandler frameStartHandler;
	private FrameEndHandler frameEndHandler;
	private BufferedImage screenBuffer;
	private int canvasWidth;
	private int canvasHeight;
	
	public MainPanel(int canvasWidth, int canvasHeight, SwingKeyboard keyboard, ErrorHandler errorHandler)
	{
		this.canvasWidth = canvasWidth;
		this.canvasHeight = canvasHeight;
		this.errorHandler = errorHandler;
		
		Player player = new Player(this, keyboard);

		int num = 3;
		float dnum = 1f / num;

		List<GraphicsDevicePainter> painters = java.util.stream.IntStream.range(0, num * num).boxed().map(i ->
		{
			float x = (i % num) * dnum;
			float y = (i / num) * dnum;
			return (GraphicsDevicePainter)new Camera(a -> a.call(x * canvasWidth, y * canvasHeight, canvasWidth * dnum, canvasHeight * dnum), new jena.engine.graphics.ColorFloatStruct(x * 0.5f, y * 0.5f, 0f, 1f), player);
		}).collect(Collectors.toList());

		painters.add(new RootCanvas(a -> a.call(150f, 150f, 300f, 300f), canvas ->
		{
			UserCanvas userCanvas = new MenuCanvas(canvas);
			userCanvas.drawButton(() -> "example", a -> a.call(10f, 10f, 100f, 100f), c -> c.call(150, 0, 150, 255), c -> c.call(0, 0, 255, 255), () -> System.out.println("click"));
		}));

		frameStartHandler = player;
		frameEndHandler = keyboard::updateKeys;
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

		float mw = (float)w / canvasWidth;
		float mh = (float)h / canvasHeight;

		int rx, ry, rw, rh;

		if (mw < mh)
		{
			rw = (int)(canvasWidth * mw);
			rh = (int)(canvasHeight * mw);
			rx = 0;
			ry = (h - rh) >>> 1;
		}
		else
		{
			rw = (int)(canvasWidth * mh);
			rh = (int)(canvasHeight * mh);
			rx = (w - rw) >>> 1;
			ry = 0;
		}
		g.drawImage(screenBuffer, rx, ry, rx + rw, ry + rh, 0, 0, canvasWidth, canvasHeight, null);
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
