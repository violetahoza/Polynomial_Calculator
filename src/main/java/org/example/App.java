package org.example;
import org.example.GUI.UserInterface;
import org.example.logic.Operations;
import org.example.models.Polynomial;
import javax.swing.*;
import java.util.ArrayList;

public class App {
    public static void main( String[] args )
    {
        Polynomial p1 = new Polynomial(), p2 = new Polynomial();
        p1 = p1.createPolynomialFromString("x^3-2x^2+x+8");
        p2 = p2.createPolynomialFromString("x-1");
        ArrayList result = Operations.divide(p1, p2);
        Polynomial q = (Polynomial) result.getFirst();
        Polynomial r = (Polynomial) result.getLast();
        String expected = "Quotient: " + q.polynomialToString() + " Remainder: " + r.polynomialToString();
        System.out.println(expected);

        JFrame frame = new UserInterface("Polynomial calculator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        // frame.pack();
        frame.setVisible(true);
    }
}
