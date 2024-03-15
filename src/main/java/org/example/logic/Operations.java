package org.example.logic;

import org.example.GUI.UserInterface;
import org.example.models.Polynomial;

import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

public class Operations {
    // method to add two polynomials
    public Polynomial add(Polynomial p1, Polynomial p2) {
        // initialize a polynomial to store the sum
        Polynomial sum = new Polynomial();

        // iterate over the entries of p1 and add them to the sum
        for(Map.Entry<Integer, Double> entry : p1.getPolynomial().entrySet()){
            int power = entry.getKey();
            double coeff = entry.getValue();
            sum.addMonom(power, coeff);
        }
        // iterate over the entries of p2
        for(Map.Entry<Integer, Double> entry : p2.getPolynomial().entrySet()){
            int power = entry.getKey();
            double coeff =  entry.getValue();

            // if the sum already contains a term with the same power, add the coefficients
            if (sum.getPolynomial().containsKey(power)) {
                double currentCoeff = sum.getPolynomial().get(power);
                double newCoeff = currentCoeff + coeff;
                if (newCoeff != 0) { // if the new coefficient is not zero, update the sum
                    sum.getPolynomial().put(power, newCoeff);
                } else sum.getPolynomial().remove(power); // if the coefficient is zero, remove the term
            } else sum.addMonom(power, coeff);// if the sum doesn't contain a term with the same power, add the term
        }
        //System.out.println(sum.polynomialToString(sum.getPolynomial()));;
        return sum;
    }

    // method to subtract two polynomials
    public Polynomial subtract(Polynomial p1, Polynomial p2) {
        Polynomial difference = new Polynomial();
        if(p1.equals(p2)){
            difference.addMonom(0, 0);
            return difference; // if p1 equals p2, return a zero polynomial
        }

        // iterate over the entries of p1 and add them to the difference
        for(Map.Entry<Integer, Double> entry : p1.getPolynomial().entrySet()){
            int power = entry.getKey();
            double coeff = entry.getValue();
            difference.addMonom(power, coeff);
        }
        for(Map.Entry<Integer, Double> entry : p2.getPolynomial().entrySet()){
            int power = entry.getKey();
            double coeff = entry.getValue();

            // if the difference already contains a term with the same power, subtract the coefficients
            if (difference.getPolynomial().containsKey(power)) {
                double currentCoeff = difference.getPolynomial().get(power);
                double newCoeff = currentCoeff - coeff;
                if (newCoeff != 0) { // if the new coefficient is not zero, update the difference
                    difference.getPolynomial().put(power, newCoeff);
                } else difference.getPolynomial().remove(power);// if the coefficient is zero, remove the term
            } else difference.addMonom(power, -coeff);// if the difference doesn't contain a term with the same power, add the negated term
        }
        return difference; // return p1 - p2
    }

    //method to multiply two polynomials
    public Polynomial multiply(Polynomial p1, Polynomial p2) {
        Polynomial product = new Polynomial();

        for(Map.Entry<Integer, Double> entry1 : p1.getPolynomial().entrySet()) // iterate over the entries of p1
        {
            int power1 = entry1.getKey();
            double coeff1 = entry1.getValue();

            for(Map.Entry<Integer, Double> entry2 : p2.getPolynomial().entrySet()){ // iterate over the entries of p2
                int power2 = entry2.getKey();
                double coeff2 = entry2.getValue();

                // compute the power and coefficient of the product term
                int productPower = power1 + power2;
                double productCoeff = coeff1 * coeff2;

                // if the product already contains a term with the same power, add the coefficients
                if (product.getPolynomial().containsKey(productPower)) {
                    double currentCoeff = product.getPolynomial().get(productPower);
                    double newCoeff = currentCoeff + productCoeff;
                    if(newCoeff != 0)
                        product.getPolynomial().put(productPower, newCoeff);
                    else product.getPolynomial().remove(power2);
                } else product.addMonom(productPower, productCoeff);
            }
        }
        return product;
    }
    public String divide(Polynomial p1, Polynomial p2) {
        // initialize a polynomial to store the quotient and one to store the dividend
        Polynomial quotient = new Polynomial();
        Polynomial dividend = new Polynomial();
        Polynomial divisor = new Polynomial();

        if (p2.getPolynomial().isEmpty() || p2.getPolynomial().containsValue(0.0) || p1.getPolynomial().isEmpty() || p1.getPolynomial().containsValue(0.0)) {
            UserInterface userInterface = new UserInterface("Polynomial calculator");
            userInterface.showErrorDialog("Division by zero is not allowed.");
            throw new ArithmeticException("Division by zero is not allowed.");   // check if the polynomials are empty or zero
        }

        // find the maximum power in both polynomials
        Integer kmax1 = Collections.max(p1.getPolynomial().entrySet(), Map.Entry.comparingByValue()).getKey();
        Integer kmax2 = Collections.max(p2.getPolynomial().entrySet(), Map.Entry.comparingByValue()).getKey();

        if(kmax1 >= kmax2) {  // determine which polynomial will be used as the dividend based on the maximum powers
            dividend.getPolynomial().putAll(p1.getPolynomial());
            divisor.getPolynomial().putAll(p2.getPolynomial());
        } else {
            dividend.getPolynomial().putAll(p2.getPolynomial());
            divisor.getPolynomial().putAll(p1.getPolynomial());
        }

        while (dividend.getPolynomial().size() >= divisor.getPolynomial().size()) {
            // find the leading terms of the dividend and divisor
            Map.Entry<Integer, Double> leadingTermDividend = dividend.getPolynomial().entrySet().iterator().next();
            Map.Entry<Integer, Double> leadingTermDivisor = divisor.getPolynomial().entrySet().iterator().next();

            // calculate the difference in powers and the coefficient of the quotient
            int powerDifference = leadingTermDividend.getKey() - leadingTermDivisor.getKey();
            double coeffQuotient = leadingTermDividend.getValue() / leadingTermDivisor.getValue();

            quotient.addMonom(powerDifference, coeffQuotient);// add the quotient term to the quotient polynomial
            Polynomial temp = new Polynomial();
            temp.addMonom(powerDifference, coeffQuotient);
            Polynomial product = multiply(divisor, temp);// multiply the divisor by the quotient term
            dividend = subtract(dividend, product);// subtract the product from the dividend
        }
        String result = "Quotient: " + quotient.polynomialToString() + " Remainder: " + dividend.polynomialToString(); // construct the result string containing both quotient and remainder
        return result;
    }

    // method to compute the derivative of a polynomial
    public Polynomial derivative(Polynomial polynomial){
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
    public Polynomial integrate(Polynomial polynomial){
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
