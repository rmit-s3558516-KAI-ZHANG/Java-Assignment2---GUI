package application;

import OwnException.*;
import OwnInterface.*;

public class YoungChild extends Person{

    public YoungChild(String name, String image, String status, String gender, int age, String state) {
        super(name, image, status, gender, age, state);
       
    }

    @Override
    public void ToBeCouple(Connection co) throws NotToBeCoupledException {
        throw new NotToBeCoupledException("making a couple when at least one member is not an adult");
    }

    @Override
    public void ToBeFriends(Connection co) throws TooYoungException {
        throw new TooYoungException("making friends with a young child", this.getAge());
    }

    @Override
    public void ToBeColleague(Connection co) throws NotToBeColleaguesException {
        throw new NotToBeColleaguesException("connecting a child in a colleague relation", ((Person)co).getAge());
    }

    @Override
    public void ToBeClassmate(Connection co) throws NotToBeClassmatesException   {
        throw new NotToBeClassmatesException("making a young child in a classmate relation!", ((Person)co).getAge());
    }
}
