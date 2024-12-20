import org.example.logic.Operations;
import org.example.models.Polynomial;
import org.junit.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OperationsTest {
    @Test
    public void addTest(){
        Polynomial p1 = new Polynomial(), p2 = new Polynomial(), sum = new Polynomial();

        p1 = Polynomial.createPolynomialFromString("2x^5+x^3+2x^2-3x+2");
        p2 = Polynomial.createPolynomialFromString("x^3+3x");
        sum = Operations.add(p1, p2);
        String result = sum.polynomialToString();

        assertEquals (result, "2.0x^5+2.0x^3+2.0x^2+2.0");
    }

    @Test
    public void subtractTest(){
        Polynomial p1 = new Polynomial(), p2 = new Polynomial(), subtract = new Polynomial();

        p1 = Polynomial.createPolynomialFromString("9x^2-3x+2");
        p2 = Polynomial.createPolynomialFromString("x^3+3x-7");
        subtract = Operations.subtract(p1, p2);
        String result = subtract.polynomialToString();

        assertEquals (result, "-x^3+9.0x^2-6.0x+9.0");
    }

    @Test
    public void multiplyTest(){
        Polynomial p1 = new Polynomial(), p2 = new Polynomial(), product = new Polynomial();

        p1 = Polynomial.createPolynomialFromString("x+1");
        p2 = Polynomial.createPolynomialFromString("x-1");
        product = Operations.multiply(p1, p2);
        String result = product.polynomialToString();

        assertEquals (result, "x^2-1.0");
    }

    @Test
    public void divideTest(){
        Polynomial p1 = new Polynomial(), p2 = new Polynomial();

        p1 = Polynomial.createPolynomialFromString("3x^4+2x");
        p2 = Polynomial.createPolynomialFromString("x");
        ArrayList result = Operations.divide(p1, p2);
        Polynomial q = (Polynomial) result.getFirst();
        Polynomial r = (Polynomial) result.getLast();
        String expected = "Quotient: " + q.polynomialToString() + " Remainder: " + r.polynomialToString();
        assertEquals (expected, "Quotient: 3.0x^3+2.0 Remainder: 0");
    }

    @Test
    public void integrateTest(){
        Polynomial p1 = new Polynomial(), integral = new Polynomial();

        p1 = Polynomial.createPolynomialFromString("3x^2-2x+2");
        integral = Operations.integrate(p1);
        String result = integral.polynomialToString();

        assertEquals (result, "x^3-x^2+2.0x");
    }

    @Test
    public void derivateTest(){
        Polynomial p1 = new Polynomial(), derivative = new Polynomial();

        p1 = Polynomial.createPolynomialFromString("3x^2-2x+2");
        derivative = Operations.derivative(p1);
        String result = derivative.polynomialToString();

        assertEquals (result, "6.0x-2.0");
    }
}
