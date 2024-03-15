import org.example.logic.Operations;
import org.example.models.Polynomial;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OperationsTest {
    @Test
    public void addTest(){
        Polynomial p1 = new Polynomial(), p2 = new Polynomial(), sum = new Polynomial();
        Operations op = new Operations();

        p1 = p1.createPolynomialFromString("2x^2-3x+2");
        p2 = p2.createPolynomialFromString("x^3+3x");
        sum = op.add(p1, p2);
        String result = sum.polynomialToString();

        assertEquals (result, "x^3+2.0x^2+2.0");
    }

    @Test
    public void subtractTest(){
        Polynomial p1 = new Polynomial(), p2 = new Polynomial(), subtract = new Polynomial();
        Operations op = new Operations();

        p1 = p1.createPolynomialFromString("2x^2-3x+2");
        p2 = p2.createPolynomialFromString("x^3+3x");
        subtract = op.subtract(p1, p2);
        String result = subtract.polynomialToString();

        assertEquals (result, "-x^3+2.0x^2-6.0x+2.0");
    }

    @Test
    public void multiplyTest(){
        Polynomial p1 = new Polynomial(), p2 = new Polynomial(), product = new Polynomial();
        Operations op = new Operations();

        p1 = p1.createPolynomialFromString("x+1");
        p2 = p2.createPolynomialFromString("x-1");
        product = op.multiply(p1, p2);
        String result = product.polynomialToString();

        assertEquals (result, "x^2-1.0");
    }

    @Test
    public void divideTest(){
        Polynomial p1 = new Polynomial(), p2 = new Polynomial();
        Operations op = new Operations();

        p1 = p1.createPolynomialFromString("x^4");
        p2 = p2.createPolynomialFromString("x");
        ArrayList result = op.divide(p1, p2);
        assertEquals (result, "Quotient: x^3 Remainder: 0");
    }

    @Test
    public void integrateTest(){
        Polynomial p1 = new Polynomial(), integral = new Polynomial();
        Operations op = new Operations();

        p1 = p1.createPolynomialFromString("3x^2-2x+2");
        integral = op.integrate(p1);
        String result = integral.polynomialToString();

        assertEquals (result, "x^3-x^2+2.0x");
    }

    @Test
    public void derivateTest(){
        Polynomial p1 = new Polynomial(), derivative = new Polynomial();
        Operations op = new Operations();

        p1 = p1.createPolynomialFromString("3x^2-2x+2");
        derivative = op.derivative(p1);
        String result = derivative.polynomialToString();

        assertEquals (result, "6.0x-2.0");
    }
}
