package org.example;
import org.example.GUI.UserInterface;
import org.example.logic.Operations;
import org.example.models.Polynomial;
import javax.swing.*;
import java.util.ArrayList;

public class App {
    public static void main( String[] args )
    {
        JFrame frame = new UserInterface("Polynomial calculator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        // frame.pack();
        frame.setVisible(true);
    }
}
