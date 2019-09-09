public class ComplexNumber {
    private Fraction a;
    private Fraction b;

    public ComplexNumber (int a, int b){
        this.a = new Fraction (a);
        this.b = new Fraction (b);
    }

    public ComplexNumber (Fraction a, Fraction b){
        this.a = a;
        this.b = b;
    }

    public ComplexNumber (int number){
        this.a = new Fraction (number);
        this.b = new Fraction (0);
    }

    //assume a+bi
    public ComplexNumber (String complexNum){
        int i = complexNum.indexOf("+");
        this.a = new Fraction(Integer.parseInt(complexNum.substring(0,i)));
        this.b = new Fraction(Integer.parseInt(complexNum.substring(i + 1), complexNum.length()-1));
    }

    public ComplexNumber add(ComplexNumber cn){
        Fraction real = this.a.add(cn.a);
        Fraction imag = this.b.add(cn.b);
        return new ComplexNumber(real, imag);
    }

    public ComplexNumber subtract(ComplexNumber cn){
        Fraction real = this.a.subtract(cn.a);
        Fraction imag = this.b.subtract(cn.b);
        return new ComplexNumber(real, imag);
    }

    public ComplexNumber multiply(ComplexNumber cn){
        //real = a1 * a2 - b1 * b2
        Fraction real = this.a.multiply(cn.a).subtract(this.b.multiply(cn.b));
        //img = a1 * b2 - b1 * a2
        Fraction imag = this.a.multiply(cn.b).subtract(this.b.multiply(cn.a));
        return new ComplexNumber(real, imag);
    }

    public ComplexNumber divide(ComplexNumber cn){
        //real = (a1*a2+b1*b2)/(a2^2 + b2^2)
        Fraction real = this.a.multiply(cn.a).add(this.b.multiply(cn.b)).divide(cn.a.multiply(cn.a).add(cn.b.multiply(cn.b)));
        //imaginary (b1*a2-a1*b2)/(a2^2 + b2^2)
        Fraction imag = this.b.multiply(cn.a).subtract(this.a.multiply(cn.b)).divide(cn.a.multiply(cn.a).add(cn.b.multiply(cn.b)));
        return new ComplexNumber(real, imag);
    }

    public String toString(){
        return this.a.toString() + "+" + this.b.toString() + "i";
    }

    public double abs(){
        return (this.a.multiply(this.a).add(this.b.multiply(this.b))).sqrt();
    }

    public int compareTo(ComplexNumber cn){
        if (cn.abs() > this.abs()){
            return -1;
        }
        else if (cn.abs() == this.abs()){
            return 0;
        }
        else {
            return 1;
        }
    }
}
