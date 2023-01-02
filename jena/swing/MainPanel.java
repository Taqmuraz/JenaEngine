package jena.swing;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import jena.engine.common.ErrorHandler;
import jena.engine.entity.Camera;
import jena.engine.entity.Player;
import jena.engine.graphics.ColorStruct;
import jena.engine.graphics.GraphicsResource;
import jena.engine.graphics.GraphicsScope;
import jena.engine.graphics.TextureHandle;
import jena.engine.io.FileResource;
import jena.engine.io.StorageFileResource;

public class MainPanel extends JPanel implements GraphicsResource
{
	SwingTextureResource imageResource;
	ErrorHandler errorHandler;
	Player player;
	Camera camera;
	
	public MainPanel(ErrorHandler errorHandler)
	{
		setDoubleBuffered(true);
		imageResource = new FileImageResource(new StorageFileResource("Image.png"), errorHandler);
		this.errorHandler = errorHandler;
		player = new Player(this);
		camera = new Camera(acceptor -> acceptor.call(getWidth(), getHeight()), new ColorStruct(0, 1, 1, 255), player);
	}

	@Override
	public void setSize(int width, int height)
	{
		setBounds(0, 0, width, height);
	}

	public void paint(Graphics g)
	{
		Graphics2D g2 = (Graphics2D)g;
		GraphicsScope scope = new SwingGraphicsScope(g2);
		camera.handleGraphics(scope);
		repaint(10, 0, 0, getWidth(), getHeight());
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