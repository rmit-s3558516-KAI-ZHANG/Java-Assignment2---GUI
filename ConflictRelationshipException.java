package OwnException;

public class ConflictRelationshipException extends Exception{
    public ConflictRelationshipException(String errMsg){
        super("The error message is"+errMsg);
        System.err.println("Can not connect two couple to parent relationships!");
    }
}
