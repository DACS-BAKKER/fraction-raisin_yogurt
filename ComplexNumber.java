/*
Name: Elven Shum, Alex Yuk
Project: Complex number class
 */

public class ComplexNumber {
    private Fraction a;
    private Fraction b;

    public ComplexNumber(int a, int b) {
        this.a = new Fraction(a);
        this.b = new Fraction(b);
    }

    public ComplexNumber(Fraction a, Fraction b) {
        this.a = a;
        this.b = b;
    }

    public ComplexNumber(int number) {
        this.a = new Fraction(number);
        this.b = new Fraction(0);
    }

    // entering a+bi no space
    public ComplexNumber(String complexNum) {
        int i = complexNum.indexOf('+');
        if (i == -1) {
            this.a = new Fraction(complexNum);
            this.b = new Fraction(0);
        }
        else {
            this.a = new Fraction(complexNum.substring(0, i));
            this.b = new Fraction(complexNum.substring(i + 1, complexNum.length() - 1));
        }
    }

    public ComplexNumber add(ComplexNumber cn) {
        Fraction a = this.a.add(cn.a);
        Fraction b = this.b.add(cn.b);
        return new ComplexNumber(a, b);
    }

    public ComplexNumber subtract(ComplexNumber cn) {
        Fraction a = this.a.subtract(cn.a);
        Fraction b = this.b.subtract(cn.b);
        return new ComplexNumber(a, b);
    }

    public ComplexNumber multiply(ComplexNumber cn) {
        Fraction a = this.a.multiply(cn.a).subtract(this.b.multiply(cn.b));
        Fraction b = this.a.multiply(cn.b).add(this.a.multiply(cn.b));
        return new ComplexNumber(a, b);
    }

    public ComplexNumber divide(ComplexNumber cn) {
        Fraction a = (this.a.multiply(cn.a).add(this.b.multiply(cn.b))).divide((cn.a.multiply((cn.a))).add((cn.b.multiply(cn.b))));
        Fraction b = (this.b.multiply(cn.a).subtract(this.a.multiply(cn.b))).divide((cn.a.multiply((cn.a))).add((cn.b.multiply(cn.b))));
        return new ComplexNumber(a, b);
    }

    public String toString(){
        if (this.b.toDecimal() == 0)
            return this.a.toString();
        return this.a.toString() + "+" + this.b.toString() + "i";
    }

    public static void main(String[]args) {
        ComplexNumber a = new ComplexNumber(new Fraction(3, 1), new Fraction(2, 1));
        System.out.println(a.divide(new ComplexNumber(new Fraction(4, 1), new Fraction(-3, 1))));
    }
}