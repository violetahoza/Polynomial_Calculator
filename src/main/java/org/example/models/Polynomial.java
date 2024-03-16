package org.example.models;

import java.util.*;
import java.util.regex.*;

import static java.lang.Double.parseDouble;

public class Polynomial {
    private Map<Integer, Double> polynomial;  // Map<power, coefficient>

    public Polynomial(){
        polynomial = new TreeMap<Integer, Double>(Collections.reverseOrder());// initialize the polynomial as a TreeMap with reverse order for keys
    }
    public void setPolynomial(Map<Integer, Double> polynomial) {
        this.polynomial = polynomial;
    }
    public Map<Integer, Double> getPolynomial() {
        return polynomial;
    }

    public void addMonom(int power, double coeff){ // method to add a new term (a monomial) to the polynomial
        if (polynomial.containsKey(power)) { //if there is already a monomial with the same power in the polynomial
            if(polynomial.get(power) + coeff != 0) //if the new coefficient is not zero
                polynomial.put(power, polynomial.get(power) + coeff); // update the value of the coefficient
            else polynomial.remove(power); // if the coefficient is zero, remove the term
        } else polynomial.put(power, coeff);
    }

    public String polynomialToString(){ //convert the polynomial to a string representation
        String string = ""; // the string in which I'll store the result
        boolean first = true; // I use it, so I don't have to display the + sign of the 1st term the polynomial

        if(polynomial.isEmpty()) // if the polynomial is empty, I want to display 0
            return "0";

        for(Map.Entry<Integer, Double> entry : polynomial.entrySet()){ // iterate over the polynomial terms
            int power = entry.getKey();
            double coefficient = entry.getValue();

            if(coefficient > 0)
            {
                if(!first) string += "+";
                if(coefficient != 1)
                    string += coefficient;
                else if (coefficient == 1) {
                    if(power == 0)
                        string += coefficient;
                }
            }
            if(coefficient < 0){
                string += "-";
                if (coefficient != -1 || power == 0)
                    string += Math.abs(coefficient);
            }
            if(power > 0){
                string += "x";
                if (power >= 2) {
                    string += "^" + power;
                }
            }
            first = false;
        }
        return string;
    }

    public static Polynomial createPolynomialFromString(String input) { //create a polynomial from a string representation
        Polynomial polynom = new Polynomial();
        Pattern pattern = Pattern.compile("([+-]?(?:\\d+\\.?\\d*)?x\\^?\\d*|[+-]?\\d+\\.?\\d*)"); // define pattern to match polynomial terms
        Matcher matcher = pattern.matcher(input);

        while (matcher.find()) { //iterate over the terms in the input string
            int power = 0;
            double coefficient = 0;

            String term = matcher.group(1); // the input string is separated in monomials ; for input string x^2+2x-3, there are 3 matches of 1 group: x^2, +2x and -3

            if (term.contains("x")) { //if the term contains 'x'
                String[] parts = term.split("x");
                if (parts.length > 0) {
                    if (parts[0].isEmpty()) coefficient = 1; //if the coefficient part is empty, set it to 1
                    else if (parts[0].equals("-")) {
                        coefficient = -1;
                    } else if (parts[0].equals("+")) {
                        coefficient = 1;
                    } else if (!parts[0].isEmpty()) {
                        coefficient = parseDouble(parts[0]); //parse the coefficient
                    }
                } else if (parts.length == 0) coefficient = 1; // if term contains only 'x', set coefficient to 1
                if (term.contains("^")) {
                    power = Integer.parseInt(term.split("\\^")[1]); //extract the power
                } else power = 1; //if the power part is not present
            } else coefficient = parseDouble(term); // if the term doesn't contain 'x', parse it as a coefficient
            if (coefficient != 0) polynom.addMonom(power, coefficient);
        }
        return polynom;
    }
}
