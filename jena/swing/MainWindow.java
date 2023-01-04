package jena.swing;

import java.awt.GraphicsEnvironment;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.Timer;


import jena.engine.common.ErrorHandler;
import jena.environment.EnvironmentVariables;
import jena.environment.variable.IntegerVariable;

public class MainWindow extends JFrame
{
	MainPanel panel;
	ErrorHandler errorHandler;
	Timer timer;
	EnvironmentVariables environmentVariables;

	public MainWindow(EnvironmentVariables environmentVariables)
	{
		this.environmentVariables = environmentVariables;

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		errorHandler = e -> 
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
					errorHandler = error -> {};
			}
		};
		panel = new MainPanel(errorHandler);
		add(panel);
		
		environmentVariables.<IntegerVariable>findVariable("fps", 
			fps -> startFrameTimer((int)(1000f / Integer.valueOf(fps.value()))),
			() -> startFrameTimer(33));

		environmentVariables.findVariable("fullscreen", 
			fs ->
			{
				setExtendedState(this.getExtendedState() | JFrame.MAXIMIZED_BOTH);
				setResizable(false);
				setUndecorated(true);
				GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().setFullScreenWindow(this);
			},
			() -> setSize(800, 600));

		setVisible(true);
	}

	void startFrameTimer(int frameDelay)
	{
		timer = new Timer(frameDelay, e ->
		{
			panel.repaint();
			panel.revalidate();
		});
		timer.start();
	}

	@Override
	public void dispose()
	{
		timer.stop();
	}

	@Override
	public void setSize(int width, int height)
	{
		super.setSize(width, height);
		panel.setSize(width, height);
	}
}
