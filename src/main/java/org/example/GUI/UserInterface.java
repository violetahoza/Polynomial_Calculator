package org.example.GUI;
import org.example.logic.Operations;
import org.example.models.Polynomial;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.zip.DataFormatException;

public class UserInterface extends JFrame implements ActionListener {
    private JPanel contentPane, numbersPanel = new JPanel(), resultPanel;
    private JLabel firstPolynomialLabel = new JLabel("First Polynomial"), secondPolynomialLabel = new JLabel("Second Polynomial"), resultLabel = new JLabel("Result");
    private JTextField firstPolynomialTextField, secondPolynomialTextField, resultTextField;
    private JButton addButton = new JButton("Add");
    private JButton subtractButton = new JButton("Subtract");
    private JButton multiplyButton = new JButton("Multiply");
    private JButton divisionButton = new JButton("Divide");
    private JButton derivationButton = new JButton("Derivative");
    private JButton integrationButton = new JButton("Integrate");
    private JLabel instructionLabel = new JLabel("The inputs should look like this: ax^n + bx^(n-1) +  ... ");

    public UserInterface(String name) {
        super(name);
        this.prepareGui();
    }

    public void prepareGui() {
        this.setSize(500, 300);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.contentPane = new JPanel(new BorderLayout());
        this.contentPane.setBackground(new Color(220, 200, 250));
        this.instructionLabel.setFont(new Font("Arial", Font.ITALIC, 14));
        this.instructionLabel.setForeground(new Color(128, 0, 128));
        this.contentPane.add(this.instructionLabel);
        this.prepareNumbersPanel();
        this.contentPane.add(this.numbersPanel, BorderLayout.NORTH);
        this.prepareResultPanel();
        this.contentPane.add(this.resultPanel, BorderLayout.SOUTH);
        this.setContentPane(this.contentPane);
        this.setVisible(true);
    }

    private void prepareResultPanel() {
        this.resultPanel = new JPanel(new BorderLayout());
        this.resultPanel.setBackground(new Color(220, 200, 250));

        this.resultLabel.setFont(new Font("Arial", Font.BOLD, 16));
        this.resultLabel.setForeground(new Color(128, 0, 128));
        this.resultPanel.add(this.resultLabel, BorderLayout.NORTH);

        this.resultTextField = new JTextField();
        this.resultTextField.setEditable(false);
        this.resultTextField.setFont(new Font("Arial", Font.ITALIC, 16));
        this.resultTextField.setHorizontalAlignment(JTextField.CENTER);
        this.resultLabel.setHorizontalAlignment(JLabel.CENTER);
        this.resultPanel.add(this.resultTextField, BorderLayout.CENTER);
        this.resultPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    }

    private void prepareNumbersPanel() {
        this.numbersPanel.setBackground(new Color(220, 200, 250));
        this.numbersPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JPanel polynomialsPanel = new JPanel(new GridLayout(2, 1, 10, 10));
        polynomialsPanel.setBackground(new Color(220, 200, 250));

        customizeLabel(firstPolynomialLabel);
        customizeLabel(secondPolynomialLabel);
        polynomialsPanel.add(this.firstPolynomialLabel);
        this.firstPolynomialTextField = new JTextField();
        customizeTextField(firstPolynomialTextField);
        polynomialsPanel.add(this.firstPolynomialTextField);
        polynomialsPanel.add(this.secondPolynomialLabel);
        this.secondPolynomialTextField = new JTextField();
        customizeTextField(secondPolynomialTextField);
        polynomialsPanel.add(this.secondPolynomialTextField);

        this.numbersPanel.setLayout(new BorderLayout());
        this.numbersPanel.add(polynomialsPanel, BorderLayout.CENTER);
        JPanel buttonsPanel = new JPanel(new GridLayout(3, 1, 10, 10));
        buttonsPanel.setBackground(new Color(220, 200, 250));

        customizeButton(addButton);
        customizeButton(subtractButton);
        customizeButton(multiplyButton);
        customizeButton(divisionButton);
        customizeButton(derivationButton);
        customizeButton(integrationButton);
        buttonsPanel.add(this.addButton);
        buttonsPanel.add(this.subtractButton);
        buttonsPanel.add(this.multiplyButton);
        buttonsPanel.add(this.divisionButton);
        buttonsPanel.add(this.derivationButton);
        buttonsPanel.add(this.integrationButton);

        buttonsPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        this.numbersPanel.add(buttonsPanel, BorderLayout.SOUTH);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        String resultString = null;
        String firstP = firstPolynomialTextField.getText();
        String secondP = secondPolynomialTextField.getText();
        Polynomial p1 = new Polynomial(), p2 = new Polynomial(), operationResult = new Polynomial();

        if (!firstP.isEmpty()){
            if(validateInput(firstPolynomialTextField.getText()) == 1)
                p1 = Polynomial.createPolynomialFromString(firstP);
            else{
                System.out.println("The first polynomial is invalid!");
                showErrorDialog("The first polynomial is invalid!");
                throw new IllegalArgumentException("The first polynomial is invalid!");
            }
        }
        if (!secondP.isEmpty()){
            if(validateInput(secondPolynomialTextField.getText()) == 1)
                p2 = Polynomial.createPolynomialFromString(secondP);
            else{
                System.out.println("The second polynomial is invalid!");
                showErrorDialog("The second polynomial is invalid!");
                throw new IllegalArgumentException("The second polynomial is invalid!");
            }
        }

        if (source == addButton || source == subtractButton || source == multiplyButton || source == divisionButton)
            if (firstP.isEmpty() || secondP.isEmpty()) {
                System.out.println("Please enter both polynomials.");
            }
        if (source == derivationButton || source == integrationButton)
            if (firstP.isEmpty() && secondP.isEmpty())
                System.out.println("Please enter at least one polynomial.");

        if (source == addButton) {
            operationResult = Operations.add(p1, p2);
            resultString = operationResult.polynomialToString();
        }
        if (source == subtractButton) {
            operationResult = Operations.subtract(p1, p2);
            resultString = operationResult.polynomialToString();
        }
        if (source == multiplyButton) {
            operationResult = Operations.multiply(p1, p2);
            resultString = operationResult.polynomialToString();
        }
        if (source == divisionButton) {
            ArrayList<Polynomial> result = Operations.divide(p1, p2);
            resultString = "Quotient: " + result.getFirst().polynomialToString()  + " Remainder: " + result.getLast().polynomialToString();
        }
        if (source == derivationButton) { //if only a polynomial is given, derive that one
            if(secondP.isEmpty() && !firstP.isEmpty())
                operationResult = Operations.derivative(p1);
            else if(!secondP.isEmpty() && firstP.isEmpty())
                operationResult = Operations.derivative(p2);
            else if (!secondP.isEmpty() && !firstP.isEmpty())//if both polynomials are given, derive the first one
                operationResult = Operations.derivative(p1);
            resultString = operationResult.polynomialToString();
        }
        if (source == integrationButton) {
            if(secondP.isEmpty() && !firstP.isEmpty())
                operationResult = Operations.integrate(p1);
            else if(!secondP.isEmpty() && firstP.isEmpty())
                operationResult = Operations.integrate(p2);
            else if (!secondP.isEmpty() && !firstP.isEmpty()) //if both polynomials are given, integrate the first one
                operationResult = Operations.integrate(p1);
            resultString = operationResult.polynomialToString();
        }
        resultTextField.setText(resultString);
    }

    private int validateInput(String input) {
        for (int i = 0; i < input.length(); i++) {
            char ch = input.charAt(i);
            // Check if the character is not 'x', '+', '-', a space, or a digit from 0 to 9
            if (ch != 'x' && ch != '+' && ch != '^' && ch != '-' && ch != ' ' && ch != '.' && !Character.isDigit(ch)) {
                return 0;
            }
        }
        return 1; //if all the characters pass the validation, return 1
    }
    private void customizeButton(JButton button) {
        button.addActionListener(this);
        button.setForeground(new Color(128, 0, 128));
        button.setFont(new Font("Serif", Font.BOLD, 14));
    }
    private void customizeLabel(JLabel label) {
        label.setFont(new Font("Arial", Font.BOLD, 16));
        label.setForeground(new Color(128, 0, 128));
    }
    private void customizeTextField(JTextField textField) {
        textField.setFont(new Font("Arial", Font.ITALIC, 14));
    }
    public static void showErrorDialog(String message) {
        Font font = new Font("Times New Roman", Font.BOLD, 14);
        UIManager.put("OptionPane.messageFont", font);
        UIManager.put("OptionPane.messageForeground", Color.RED);
        JOptionPane.showMessageDialog(null, message, "Input Error", JOptionPane.ERROR_MESSAGE);
    }
}