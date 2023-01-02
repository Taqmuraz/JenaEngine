package jena.swing;

import java.awt.*;
import java.util.Scanner;

public class Program
{
	public static void main(String[] args)
	{
		System.setProperty("sun.java2d.uiScale", "1.0"); // to disable Windows UI scaling

		EventQueue.invokeLater(() ->
		{
			try
			{
				new MainWindow();
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
