package org.example.application;
import org.example.GUI.UserInterface;
import javax.swing.*;

public class App {
    public static void main( String[] args )
    {
        JFrame frame = new UserInterface("Polynomial calculator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setVisible(true);
    }
}
