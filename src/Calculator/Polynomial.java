package Calculator;

import java.util.ArrayList;

class Polynomial {
    ArrayList<Term> terms = new ArrayList<>();

    void addTerm(Term term) {
        terms.add(term);
    }

    double valueAt(int x0) {
        double answer = 0;
        for (int i = 0; i < terms.size(); i++) {
            answer += terms.get(i).valueAt(x0);
        }
        return answer;
    }

    Polynomial derivative() {
        Polynomial answer = new Polynomial();
        for (int i = 0; i < terms.size(); i++) {
            answer.addTerm(terms.get(i).derivative());
        }
        return answer;
    }

    Polynomial integrate() {
        Polynomial answer = new Polynomial();
        for (int i = 0; i < terms.size(); i++) {
            answer.addTerm(terms.get(i).integrate());
        }
        return answer;
    }


    Polynomial simplification() {
        for (int i = 0; i < terms.size(); i++) {
            Term zero = terms.get(i);
            if (zero.coefficient == 0) {
                terms.remove(zero);
            }
        }
        ArrayList<Term> a = this.terms;
        Polynomial answer = new Polynomial();
        for(int i=a.size()-1; i>=0;i--){
            for(int j=0; j<i; j++){
                if(a.get(i).power == a.get(j).power){
                    a.get(i).coefficient += a.get(j).coefficient;
                    a.get(j).coefficient = 0;
                    break;
                }
            }
        }
        answer.terms = a;
        return answer;
    }

    public StringBuilder show() {
        int counter = 0;
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < terms.size(); i++) {
            if (terms.get(i).power == 0) {
                if (terms.get(i).coefficient > 0) {
                    if(counter == 0){
                        str.append(terms.get(i).coefficient).append(" ");
                    }else {
                        str.append(" +").append(terms.get(i).coefficient).append(" ");
                    }
                } else {
                    if(counter == 0){
                        str.append(terms.get(i).coefficient).append(" ");
                    }else {
                        str.append(terms.get(i).coefficient).append(" ");
                    }
                }
            } else {
                if (terms.get(i).coefficient > 0) {
                    if(counter == 0){
                        str.append(terms.get(i).coefficient).append("x^").append(terms.get(i).power).append(" ");
                    }else {
                        str.append(" +").append(terms.get(i).coefficient).append("x^").append(terms.get(i).power).append(" ");
                    }
                } else {
                    if(counter == 0){
                        str.append(terms.get(i).coefficient).append("x^").append(terms.get(i).power).append(" ");
                    }else {
                        str.append(terms.get(i).coefficient).append("x^").append(terms.get(i).power).append(" ");
                    }
                }
            }
            counter++;
        }
        return str;
    }
}