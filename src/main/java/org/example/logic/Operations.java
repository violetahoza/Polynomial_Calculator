package org.example.logic;

import org.example.GUI.UserInterface;
import org.example.models.Polynomial;

import java.awt.*;
import java.util.*;

public class Operations {
    // method to add two polynomials
    public static Polynomial add(Polynomial p1, Polynomial p2) {
        Polynomial sum = new Polynomial(); // initialize a polynomial to store the sum

        // iterate over the entries of p1 and add them to the sum
        for(Map.Entry<Integer, Double> entry : p1.getPolynomial().entrySet()){
            sum.addMonom(entry.getKey(), entry.getValue());
        }
        // iterate over the entries of p2
        for(Map.Entry<Integer, Double> entry : p2.getPolynomial().entrySet()){
            sum.addMonom(entry.getKey(), entry.getValue()); //the case when there already exists a term with the same power is treated in the method addMonom
        }
        //System.out.println(sum.polynomialToString(sum.getPolynomial()));;
        return sum;
    }

    // method to subtract two polynomials
    public static Polynomial subtract(Polynomial p1, Polynomial p2) {
        Polynomial difference = new Polynomial();// initialize a polynomial to store the difference
        if(p1.equals(p2)){
            difference.addMonom(0, 0);
            return difference; // if p1 equals p2, return a zero polynomial
        }
        // iterate over the entries of p1 and add them to the difference
        for(Map.Entry<Integer, Double> entry : p1.getPolynomial().entrySet()){
            difference.addMonom(entry.getKey(), entry.getValue());
        }
        // iterate over the entries of p2 and add them to the difference with sign - in front
        for(Map.Entry<Integer, Double> entry : p2.getPolynomial().entrySet()){
            difference.addMonom(entry.getKey(), -entry.getValue()); //the case when there already exists a term with the same power is treated in the method addMonom
        }
        return difference; // return p1 - p2
    }

    //method to multiply two polynomials
    public static Polynomial multiply(Polynomial p1, Polynomial p2) {
        Polynomial product = new Polynomial();// initialize a polynomial to store the product

        for(Map.Entry<Integer, Double> entry1 : p1.getPolynomial().entrySet()) // iterate over the entries of p1
        {
            int power1 = entry1.getKey();
            double coeff1 = entry1.getValue();

            for(Map.Entry<Integer, Double> entry2 : p2.getPolynomial().entrySet()){ // iterate over the entries of p2
                int power2 = entry2.getKey();
                double coeff2 = entry2.getValue();

                // compute the power and coefficient of the product term
                int productPower = power1 + power2; //the powers are added
                double productCoeff = coeff1 * coeff2; // the coefficients are multiplied

                product.addMonom(productPower, productCoeff); //the case when there already exists a term with the same power is treated in the method addMonom
            }
        }
        return product;
    }
    public static ArrayList<Polynomial> divide(Polynomial p1, Polynomial p2) {
        Polynomial quotient = new Polynomial(); // initialize a polynomial to store the quotient
        Polynomial dividend = new Polynomial(); // initialize a polynomial to store the dividend (at the end, it will contain the remainder)
        Polynomial divisor = new Polynomial(); // initialize a polynomial to store the divisor
        ArrayList<Polynomial> result = new ArrayList<Polynomial>();//the 1st element will be the quotient, the second will be the remainder

        if (p2.getPolynomial().isEmpty() || p2.getPolynomial().containsValue(0.0) || p1.getPolynomial().isEmpty() || p1.getPolynomial().containsValue(0.0)) {
            UserInterface userInterface = new UserInterface("Polynomial calculator");
            userInterface.showErrorDialog("Division by zero is not allowed.");
            throw new ArithmeticException("Division by zero is not allowed."); // check if the polynomials are empty or zero
        }

        // find the maximum power in both polynomials
        Integer kmax1 = Collections.max(p1.getPolynomial().entrySet(), Map.Entry.comparingByKey()).getKey();
        Integer kmax2 = Collections.max(p2.getPolynomial().entrySet(), Map.Entry.comparingByKey()).getKey();

        if(kmax1 >= kmax2) {  // determine which polynomial will be used as the dividend based on the maximum powers
            dividend.getPolynomial().putAll(p1.getPolynomial());
            divisor.getPolynomial().putAll(p2.getPolynomial());
        } else {
            dividend.getPolynomial().putAll(p2.getPolynomial());
            divisor.getPolynomial().putAll(p1.getPolynomial());
        }

        while (dividend.getPolynomial().size() >= divisor.getPolynomial().size()) {
            // find the leading terms of the dividend and divisor (the terms with the greatest power)
            Map.Entry<Integer, Double> leadDividend = Collections.max(dividend.getPolynomial().entrySet(), Map.Entry.comparingByKey());
            Map.Entry<Integer, Double> leadDivisor = Collections.max(divisor.getPolynomial().entrySet(), Map.Entry.comparingByKey());

            // calculate the difference in powers and the coefficient of the quotient
            int powerDifference = leadDividend.getKey() - leadDivisor.getKey();
            double coeffQuotient = leadDividend.getValue() / leadDivisor.getValue();

            quotient.addMonom(powerDifference, coeffQuotient);// add the quotient term to the quotient polynomial
            Polynomial temp = new Polynomial();
            temp.addMonom(powerDifference, coeffQuotient);
            Polynomial product = multiply(divisor, temp);// multiply the divisor by the quotient term
            dividend = subtract(dividend, product);// subtract the product from the dividend
        }
        result.add(quotient);
        result.add(dividend);
        return result;
    }

    // method to compute the derivative of a polynomial
    public static Polynomial derivative(Polynomial polynomial){
        Polynomial derivative = new Polynomial();
        for (Map.Entry<Integer, Double> entry : polynomial.getPolynomial().entrySet()) {
            int power = entry.getKey();
            double coeff = entry.getValue();
            if (power >= 1)
                derivative.addMonom(power - 1, coeff * power);
        }
        return derivative;
    }

    // method to compute the integral of a polynomial
    public static Polynomial integrate(Polynomial polynomial){
        Polynomial integral = new Polynomial();
        for (Map.Entry<Integer, Double> entry : polynomial.getPolynomial().entrySet()) {
            int power = entry.getKey();
            double coeff = entry.getValue();
            if (power >= 0)
                integral.addMonom(power + 1, coeff / (power + 1.0));
        }
        return integral;
    }
}
