package jena.main;

import java.awt.EventQueue;
import java.util.Scanner;

import jena.environment.EnvironmentVariables;
import jena.environment.StandardEnvironmentVariables;
import jena.environment.variable.StringVariable;
import jena.jogl.JOGLWindow;
import jena.swing.MainWindow;

public class Program
{
    public static void main(String[] args)
    {
        System.setProperty("sun.java2d.uiScale", "1.0"); // to disable Windows UI scaling

        EventQueue.invokeLater(() ->
        {
            try
            {
                EnvironmentVariables variables = new StandardEnvironmentVariables(args);
                variables.<StringVariable>findVariable("graphics", v ->
                {
                    switch(v.value())
                    {
                        case "opengl": new JOGLWindow(variables); break;
                        default: new MainWindow(variables);
                    }
                },
                () -> new MainWindow(variables));
            }
            catch(Throwable error)
            {
                System.out.println(error.toString());
                for (Object trace : error.getStackTrace())
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
