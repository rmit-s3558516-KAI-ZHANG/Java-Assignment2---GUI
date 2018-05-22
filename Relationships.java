package application;

import java.util.Comparator;
import java.util.TreeSet;

public class Relationships {
    private Person p1=null;
    private Person p2=null;
    private String re=null;
    

    
    public Relationships(Person p1, Person p2, String re) {
        this.p1 = p2;
        this.p2 = p1;
        this.re = re;
    }
    @Override
    public int hashCode() {
        return 1;
    }
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Relationships))
            return false;
        else if (((Relationships) obj).getP1().equals(getP2()) && ((Relationships) obj).getP2().equals(getP1())
                && ((Relationships) obj).getRe().equals(getRe()))
            return true;
        else if (((Relationships) obj).getP1().equals(getP1()) && ((Relationships) obj).getP2().equals(getP2())
                && ((Relationships) obj).getRe().equals(getRe()))
            return true;
        return false;
    }
    
    public Person getP1() {
        return p1;
    }
    public void setP1(Person p1) {
        this.p1 = p1;
    }
    public Person getP2() {
        return p2;
    }
    public void setP2(Person p2) {
        this.p2 = p2;
    }
    public String getRe() {
        return re;
    }
    public void setRe(String re) {
        this.re = re;
    }

    

}
