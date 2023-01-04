package jena.swing;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import jena.engine.common.ErrorHandler;
import jena.engine.entity.Camera;
import jena.engine.entity.Player;
import jena.engine.graphics.ColorByteStruct;
import jena.engine.graphics.GraphicsResource;
import jena.engine.graphics.GraphicsDevice;
import jena.engine.graphics.TextureHandle;
import jena.engine.io.FileResource;
import jena.engine.io.StorageFileResource;

public class MainPanel extends JPanel implements GraphicsResource
{
	SwingTextureResource imageResource;
	ErrorHandler errorHandler;
	Player player;
	Camera[] cameras;
	
	public MainPanel(ErrorHandler errorHandler)
	{
		setDoubleBuffered(true);
		imageResource = new FileImageResource(new StorageFileResource("Image.png"), errorHandler);
		this.errorHandler = errorHandler;
		player = new Player(this);
		cameras = new Camera[] 
		{
			new Camera(acceptor -> acceptor.call(0, 0, getWidth(), getHeight()), new ColorByteStruct(0, 50, 50, 255), player),
			new Camera(acceptor -> acceptor.call(250, 250, 200, 300), new ColorByteStruct(50, 50, 150, 255), player),
			new Camera(acceptor -> acceptor.call(1000, 600, 700, 400), new ColorByteStruct(50, 150, 150, 255), player),
		};
	}

	@Override
	public void setSize(int width, int height)
	{
		setBounds(0, 0, width, height);
	}

	public void paint(Graphics g)
	{
		Graphics2D g2 = (Graphics2D)g;
		GraphicsDevice device = new SwingGraphicsDevice(g2);
		for (Camera camera : cameras) camera.paint(device);
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
