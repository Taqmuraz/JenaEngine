package jena.main;

import java.awt.EventQueue;
import java.util.Scanner;

import jena.environment.StandardEnvironmentVariables;
import jena.swing.MainWindow;

public class Program
{
    public static void main(String[] args)
    {
        System.setProperty("sun.java2d.uiScale", "1.0"); // to disable Windows UI scaling
        System.out.println("OpenGL version : " + com.jogamp.opengl.GL.GL_VERSION);

        EventQueue.invokeLater(() ->
        {
            try
            {
                new MainWindow(new StandardEnvironmentVariables(args));
            }
            catch(Throwable error)
            {
                System.out.println(error.toString());
                for (var trace : error.getStackTrace())
                {
                    System.out.println(trace);
                }
                try(Scanner scanner = new Scanner(System.in))
                {
                    scanner.nextInt();
                }
            }
        });
    }
}
