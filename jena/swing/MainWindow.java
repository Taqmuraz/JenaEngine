package jena.swing;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import jena.engine.common.ErrorHandler;

public class MainWindow extends JFrame
{
	MainPanel panel;
	ErrorHandler errorHandler;

	public MainWindow()
	{
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		setVisible(true);

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

		setSize(800, 600);
	}
	@Override
	public void setSize(int width, int height)
	{
		super.setSize(width, height);
		panel.setSize(width, height);
	}
}