import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.awt.image.*;
import javax.imageio.*;
import java.awt.geom.*;
import java.time.*;
import java.time.temporal.*;

public class Program
{
	public static void main(String[] args)
	{
		EventQueue.invokeLater(() -> new MainWindow());
	}
}
class MainPanel extends JPanel
{
	public MainPanel(int width, int height)
	{
		setBounds(0, 0, width, height);
		setDoubleBuffered(true);
		clearColor = new Color(0, 0, 100, 255);
		try
		{
			image = ImageIO.read(new File("Image.png"));
		}
		catch(IOException ex)
		{
		}
	}
	Color clearColor;
	BufferedImage image;

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
		transform.rotate(Math.toRadians(time * 360f), (image.getWidth() >> 1) + x, (image.getHeight() >> 1) + y);
		g2.setTransform(transform);
		g.drawImage(image, x, y, null);
		
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
		add(new MainPanel(getWidth(), getHeight()));
	}
	
}
