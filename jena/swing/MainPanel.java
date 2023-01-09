package jena.swing;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import jena.engine.common.ErrorHandler;
import jena.engine.entity.Camera;
import jena.engine.entity.Player;
import jena.engine.graphics.GraphicsResource;
import jena.engine.graphics.GraphicsDevice;
import jena.engine.graphics.GraphicsDevicePainter;
import jena.engine.graphics.TextureHandle;
import jena.engine.io.FileResource;

public class MainPanel extends JPanel implements GraphicsResource
{
	private ErrorHandler errorHandler;
	private Player player;
	private GraphicsDevicePainter rootPainter;
	private BufferedImage screenBuffer;
	private int canvasWidth;
	private int canvasHeight;
	private SwingKeyboard keyboard;
	
	public MainPanel(int canvasWidth, int canvasHeight, SwingKeyboard keyboard, ErrorHandler errorHandler)
	{
		this.canvasWidth = canvasWidth;
		this.canvasHeight = canvasHeight;
		this.keyboard = keyboard;
		this.errorHandler = errorHandler;
		
		player = new Player(this, keyboard);

		var cameras = java.util.stream.IntStream.range(0, 100).boxed().map(i ->
		{
			float x = (i % 10) * 0.1f;
			float y = (i / 10) * 0.1f;
			return new Camera(a -> a.call(x * canvasWidth, y * canvasHeight, canvasWidth * 0.1f, canvasHeight * 0.1f), new jena.engine.graphics.ColorFloatStruct(x * 0.5f, y * 0.5f, 0f, 1f), player);
		}).toList();

		rootPainter = device ->
		{
			for(Camera camera : cameras)
			{
				camera.paint(device);
			}
		};

		screenBuffer = new BufferedImage(canvasWidth, canvasHeight, BufferedImage.TYPE_INT_ARGB);
	}

	@Override
	public void setSize(int width, int height)
	{
		setBounds(0, 0, width, height);
	}

	public void paint(Graphics g)
	{
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

		keyboard.updateKeys();
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
