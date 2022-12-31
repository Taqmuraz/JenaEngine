package jena.swing;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JOptionPane;

import jena.engine.common.Action;
import jena.engine.common.ActionThrows;
import jena.engine.common.ActionThrowsHandler;
import jena.engine.common.ErrorHandler;
import jena.engine.common.FunctionThrows;
import jena.engine.common.FunctionThrowsHandler;
import jena.engine.io.StorageFileResource;

import java.awt.*;
import java.io.*;
import java.lang.reflect.Method;
import java.awt.image.*;
import javax.imageio.*;
import java.awt.geom.*;
import java.time.*;
import java.time.temporal.*;
import java.util.Arrays;

public class Program
{
	public static void main(String[] args)
	{
		EventQueue.invokeLater(() -> new MainWindow());
	}
}

interface CloseHandler
{
	void close();
}

class MainPanel extends JPanel
{
	public MainPanel(int width, int height, ErrorHandler errorHandler)
	{
		setBounds(0, 0, width, height);
		setDoubleBuffered(true);
		clearColor = new Color(0, 0, 100, 255);
		imageResource = new FileImageResource(new StorageFileResource("Image.png"), errorHandler);
	}
	Color clearColor;
	SwingTextureResource imageResource;

	public void paint(Graphics g)
	{
		Graphics2D g2 = (Graphics2D)g;
		Temporal currentFrame = LocalTime.now();
		Duration frameTime = Duration.between(LocalTime.MIN, currentFrame);
		
		float time = (float)(frameTime.getSeconds() + frameTime.getNano() * 0.000000001);
	
		g.setColor(clearColor);
		g.fillRect(0, 0, getWidth(), getHeight());

		int x = 150, y = 150;
		AffineTransform transform = new AffineTransform();
		
		imageResource.accept(descriptor -> 
			descriptor.acceptImage(image ->
			descriptor.acceptSize((imageWidth, imageHeight) ->
		{
			transform.rotate(Math.toRadians(time * 360f), (imageWidth >> 1) + x, (imageHeight >> 1) + y);
			g2.setTransform(transform);
			g.drawImage(image, x, y, null);
		})));
		
		repaint(10, 0, 0, getWidth(), getHeight());
	}
}
class MainWindow extends JFrame
{
	public MainWindow()
	{
		setSize(800, 600);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		add(new MainPanel(getWidth(), getHeight(),  e -> 
		{
			int choice = JOptionPane.showOptionDialog(this, e, "Error message",
				JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE, null, new String[] 
				{
					"Continue",
					"Finish the program"
				}, e);
			switch(choice)
			{
				case 1:
					dispose();
			}
		}));
	}
}
