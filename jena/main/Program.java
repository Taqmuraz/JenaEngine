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

        //new FileEncoder(new FileStorageResource("../resources.txt")).encode(new ResourcesEncoder(), System.out::println);
        //new FileDecoder(new FileStorageResource("../resources.txt")).decode(new ResourcesDecoder(), System.out::println);

        EventQueue.invokeLater(() ->
        {
            try
            {
                EnvironmentVariables variables = new StandardEnvironmentVariables(args);
                variables.<StringVariable>findVariable("graphics", v ->
                {
                    switch(v.value())
                    {
                        default: new JOGLWindow(variables); break;
                        case "swing": new MainWindow(variables); break;
                    }
                },
                () -> new JOGLWindow(variables));
            }
            catch(Throwable error)
            {
                while(error.getCause() != null) error = error.getCause();

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
