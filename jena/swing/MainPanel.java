package jena.swing;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import jena.engine.common.ErrorHandler;
import jena.engine.entity.Camera;
import jena.engine.entity.Player;
import jena.engine.entity.Time;
import jena.engine.graphics.ColorByteStruct;
import jena.engine.graphics.GraphicsResource;
import jena.engine.graphics.GraphicsDevice;
import jena.engine.graphics.TextureHandle;
import jena.engine.io.FileResource;

public class MainPanel extends JPanel implements GraphicsResource
{
	private ErrorHandler errorHandler;
	private Player player;
	private Camera[] cameras;
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

		cameras = new Camera[] 
		{
			new Camera(acceptor -> acceptor.call(0, 0, canvasWidth, canvasHeight), new ColorByteStruct(0, 50, 50, 255), player),
			new Camera(acceptor -> acceptor.call(canvasWidth * (0.1f + (float)Math.cos(Time.time()) * 0.1f), canvasHeight * 0.25f, canvasWidth * 0.2f, canvasHeight * 0.4f), new ColorByteStruct(50, 50, 150, 255), player),
			//new Camera(acceptor -> acceptor.call(canvasWidth * 0.4f, canvasHeight * (0.6f + (float)Math.sin(Time.time()) * 0.1f), canvasWidth * (0.3f + (float)Math.cos(Time.time()) * 0.2f), canvasHeight * 0.3f), new ColorByteStruct(50, 150, 150, 255), player),
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
		screen.clearRect(0, 0, canvasWidth, canvasHeight);
		for (Camera camera : cameras) camera.paint(device);
		g.drawImage(screenBuffer, 0, 0, getWidth(), getHeight(), 0, 0, canvasWidth, canvasHeight, null);

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
