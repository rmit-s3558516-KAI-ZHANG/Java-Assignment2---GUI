package OwnInterface;

import OwnException.*;

public interface Connection {
    
    public abstract void ToBeClassmate(Connection co) throws  ConflictRelationshipException, NotToBeClassmatesException;

    public abstract void ToBeColleague(Connection co) throws NotToBeColleaguesException, ConflictRelationshipException;

    public abstract void ToBeCouple(Connection co) throws NotToBeCoupledException, NoAvailableException, ConflictRelationshipException;

    public abstract void ToBeFriends(Connection co) throws TooYoungException, NotToBeFriendsException, ConflictRelationshipException;

    public abstract void ToBeParent(Connection co) throws ConflictRelationshipException, NotToBeParentException;

}
