public class Fraction {
    private int numerator;
    private int denominator;

    // constructor takes the numerator and denominator and sets up the fraction
    public Fraction(int numerator, int denominator) {
        int gcd = gcd(numerator, denominator);
        this.numerator = this.numerator / gcd;
        this.denominator = this.denominator / gcd;
        //ask bakker how to handle illegal 0, -num
    }

    // constructor takes a whole number and sets up the fraction
    public Fraction(int number){
        this.numerator = number;
        this.denominator = 1;
    }

    // constructor takes a string representation of a fraction, say “5/12”, and
    // sets up the fraction
    public Fraction(String fraction){
        int i = fraction.indexOf("/");
        this.numerator = Integer.parseInt(fraction.substring(0,i));
        this.denominator = Integer.parseInt(fraction.substring(i+1);
    }

    // adds fraction f to this fraction - returns the resulting fraction
    public Fraction add(Fraction f){
        this.numerator = this.numerator + f.numerator * this.denominator;
        this.denominator = this.denominator * f.denominator;
        int gcd = gcd(this.numerator, this.denominator);
        return new Fraction(this.numerator / gcd, this.denominator / gcd);
    }

    // subtracts fraction f from this fraction - returns the resulting fraction
    public Fraction subtract(Fraction f){
        this.numerator = this.numerator - f.numerator * this.denominator;
        this.denominator = this.denominator * f.denominator;
        int gcd = gcd(this.numerator, this.denominator);
        return new Fraction(this.numerator / gcd, this.denominator / gcd);
    }

    // multiplies fraction f by this fraction -  returns the resulting fraction
    public Fraction multiply(Fraction f){
        this.numerator = this.numerator * f.numerator;
        this.denominator = this.denominator * f.denominator;
        int gcd = gcd(this.numerator, this.denominator);
        return new Fraction(this.numerator / gcd, this.denominator / gcd);
    }

    // divides fraction f to this fraction - returns the resulting fraction
    public Fraction divide(Fraction f){
        this.numerator = this.numerator * f.denominator;
        this.denominator = this.denominator * f.numerator;
        int gcd = gcd(this.numerator, this.denominator);
        return new Fraction(this.numerator / gcd, this.denominator / gcd);
    }

    public int gcd(int x, int y){
        if (x % y ==0)
            return y;
        int temp = y;
        y = x % y;
        return gcd(temp, y);
    }

    // returns the decimal representation (approximation) of the fraction
    public double toDecimal(){
        return (double) this.numerator / this.denominator;
    }

    // compares this fraction to another fraction
    // return a negative number if this fraction is less than the other
    // return 0 if the fractions are equal
    // return a positive number if this fraction is greater than the other
    public int compareTo(Fraction f){
        if (f.toDecimal() > this.toDecimal()){
            return -1;
        }
        else if (f.toDecimal() == this.toDecimal()){
            return 0;
        }
        else {
            return 1;
        }
    }

    // returns a string representation of the fraction
    public String toString(){
        return this.numerator + "/" + this.denominator;
    }

    // do unit testing of this class
    public static void main(String[] args){
    }

}
