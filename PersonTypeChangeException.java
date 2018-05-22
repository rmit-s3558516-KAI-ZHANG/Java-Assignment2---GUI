package OwnException;

public class PersonTypeChangeException extends Exception{
    public PersonTypeChangeException(String errMsg){
        super("The error message is "+errMsg);
        System.err.println("can not update this person to other type of person, you need delate first, then readd it!");
    }
}
