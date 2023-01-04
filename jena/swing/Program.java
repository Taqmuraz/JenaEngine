package jena.swing;

import java.awt.*;
import java.util.Scanner;

import jena.environment.StandardEnvironmentVariables;

public class Program
{
	public static void main(String[] args)
	{
		System.setProperty("sun.java2d.uiScale", "1.0"); // to disable Windows UI scaling

		EventQueue.invokeLater(() ->
		{
			try
			{
				new MainWindow(new StandardEnvironmentVariables(args));
			}
			catch(Throwable error)
			{
				System.out.println(error);
				try(Scanner scanner = new Scanner(System.in))
				{
					scanner.nextInt();
				}
			}
		});
	}
}
