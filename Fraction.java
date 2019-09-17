/*
Name: Elven Shum, Alex Yuk
Project: Fraction Class
 */

public class Fraction {
    private int numerator;
    private int denominator;

    public Fraction(int numerator, int denominator) {
        int gcd = gcd(numerator, denominator);
        this.numerator = numerator / gcd;
        this.denominator = denominator / gcd;
    }

    public Fraction(int number) {
        this.numerator = number;
        this.denominator = 1;
    }

    public Fraction(String fraction) {
        int i = fraction.indexOf("/");
        if (i != -1) {
            this.numerator = Integer.parseInt(fraction.substring(0, i));
            this.denominator = Integer.parseInt(fraction.substring(i + 1));
        } else {
            this.numerator = Integer.parseInt(fraction);
            this.denominator = 1;
        }
    }

    public Fraction add(Fraction f) {
        int n =  this.numerator * f.denominator + f.numerator * this.denominator;
        int d = this.denominator * f.denominator;
        int gcd = gcd(n, d);
        return new Fraction(n / gcd, d / gcd);
    }

    public Fraction subtract(Fraction f) {
        int n = this.numerator * f.denominator - f.numerator * this.denominator;
        int d = this.denominator * f.denominator;
        int gcd = gcd(n, d);
        return new Fraction(n / gcd, d / gcd);
    }

    public Fraction multiply(Fraction f) {
        int n = this.numerator * f.numerator;
        int d = this.denominator * f.denominator;
        int gcd = gcd(n, d);
        return new Fraction(n / gcd, d / gcd);
    }

    public Fraction divide(Fraction f) {
        int n = this.numerator * f.denominator;
        int d = this.denominator * f.numerator;
        int gcd = gcd(n, d);

        return new Fraction(n / gcd, d / gcd);
    }

    private int gcd(int x, int y) {
        if (x % y == 0)
            return y;
        return gcd(y, x % y);
    }

    public double toDecimal() {
        return (double) this.numerator / this.denominator;
    }

    public String toString() {
        if (this.denominator == 1)
            return Integer.toString(this.numerator);

        return this.numerator + "/" + this.denominator;
    }

    public int compareTo(Fraction f) {
        if (this.toDecimal() < f.toDecimal())
            return 1;
        if (this.toDecimal() > f.toDecimal())
            return -1;
        return 0;
    }

    public static void main(String[]args) {
        Fraction a = new Fraction (0, 22);
        System.out.println(a.gcd(0, 22));
    }
}
