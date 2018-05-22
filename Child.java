package application;

import java.io.File;
import java.io.IOException;

import OwnException.*;
import OwnInterface.*;

public class Child extends Person {

    public Child(String name, String image, String status, String gender, int age, String state) {
        super(name, image, status, gender, age, state);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void ToBeCouple(Connection co) throws NotToBeCoupledException {
        throw new NotToBeCoupledException("making a couple when at least one member is not an adult");
    }

    @Override
    public void ToBeFriends(Connection co) throws TooYoungException, NotToBeFriendsException, ConflictRelationshipException{
        if(((Person)co).getName().equals(this.getName())){
            throw new ConflictRelationshipException("can not add itself");
        }
        if (co instanceof Child) {
            if (this.getAge() - ((Person) co).getAge() > 3) {
                throw new NotToBeFriendsException("connecting two children with an age gap larger than 3",
                        ((Person) co).getAge());
            } else {
                Mininet.relat.add(new Relationships(this, (Person) co, "friends"));
            }
        } else if (co instanceof Adult) {
            throw new NotToBeFriendsException("making an adult and a child to be friends!");
        } else {
            throw new TooYoungException("making friends with a young child", ((Person) co).getAge());
        }
    }

    @Override
    public void ToBeColleague(Connection co) throws NotToBeColleaguesException {
        throw new NotToBeColleaguesException("connecting a child in a colleague relation", ((Person) co).getAge());
    }

}
