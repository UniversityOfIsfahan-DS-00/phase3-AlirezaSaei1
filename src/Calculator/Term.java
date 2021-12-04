package Calculator;

class Term {
    double coefficient;
    int power;

    Term(double coefficient, int power) {
        this.coefficient = coefficient;
        this.power = power;
    }

    double valueAt(double x0) {
        return Math.pow(x0, power) * coefficient;
    }

    Term derivative() {
        int p = this.power - 1;
        double co = this.coefficient * this.power;
        return new Term(co, p);
    }

    Term integrate() {
        int p = (this.power + 1);
        double c = (this.coefficient / p);
        return new Term(c, p);
    }
}