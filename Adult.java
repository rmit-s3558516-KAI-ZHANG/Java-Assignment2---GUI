package application;



import OwnException.*;
import OwnInterface.*;

public class Adult extends Person {

    public Adult(String name, String image, String status, String gender, int age, String state) {
        super(name, image, status, gender, age, state);
    }
    public Relationships ppp;

    @Override
    public void ToBeCouple(Connection co)
            throws NoAvailableException, NotToBeCoupledException, ConflictRelationshipException {
        if (((Person) co).getName().equals(this.getName())) {
            throw new ConflictRelationshipException("can not add itself");
        }

        if (co instanceof Adult) {
            for (Relationships r : Mininet.relat) {
                if (r.getP1().equals(this) && r.getRe().equals("couple")
                        || r.getP2().equals(this) && r.getRe().equals("couple")
                        || r.getP1().equals(co) && r.getRe().equals("couple")
                        || r.getP2().equals(co) && r.getRe().equals("couple")) {
                    throw new NoAvailableException(
                            "at least one of adults is already connected with other adult as couple!");
                }
                if(r.getP1().equals(this) && r.getP2().equals(co) && r.getRe().equals("parent")
                        || r.getP2().equals(this) && r.getP1().equals(co) && r.getRe().equals("parent")){
                    throw new ConflictRelationshipException("can not connect a parent and a his child as couple relationships!");
                }
            }
            Mininet.relat.add(new Relationships(this, (Person) co, "couple"));
        } else {
            throw new NotToBeCoupledException("making a couple when at least one member is not an adult");
        }
    }

    @Override
    public void ToBeFriends(Connection co)
            throws TooYoungException, NotToBeFriendsException, ConflictRelationshipException {
        if (((Person) co).getName().equals(this.getName())) {
            throw new ConflictRelationshipException("can not add itself");
        }
        if (co instanceof Adult) {
            Mininet.relat.add(new Relationships(this, (Person) co, "friends"));
        } else if (co instanceof Child) {
            throw new NotToBeFriendsException("make an adult and a child to be friends!");
        } else {
            throw new TooYoungException("making friends with a young child", ((Person) co).getAge());
        }
    }

    @Override
    public void ToBeColleague(Connection co) throws NotToBeColleaguesException, ConflictRelationshipException {
        if (((Person) co).getName().equals(this.getName())) {
            throw new ConflictRelationshipException("can not add itself");
        }
        if (co instanceof Adult) {
            ppp=new Relationships(this, (Person) co, "colleagues");
            Mininet.relat.add(ppp);
        } else {
            throw new NotToBeColleaguesException("connecting a child in a colleague relation", ((Person) co).getAge());
        }
    }

    @Override
    public void ToBeParent(Connection co) throws ConflictRelationshipException {
        if (((Person) co).getName().equals(this.getName())) {
            throw new ConflictRelationshipException("can not add itself");
        }
        if (co instanceof Adult) {
            for (Relationships r : Mininet.relat) {
                if (r.getP1().equals(this) && r.getP2().equals(co) && r.getRe().equals("couple")
                        || r.getP2().equals(this) && r.getP1().equals(co) && r.getRe().equals("couple")) {
                    throw new ConflictRelationshipException("can not connect couple to parent relationships!");
                }
            }
        }
        ppp=new Relationships(this, (Person) co, "parent");
        Mininet.relat.add(ppp);
    }
}
