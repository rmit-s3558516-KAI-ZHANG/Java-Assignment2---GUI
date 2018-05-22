package OwnException;

public class NoSuchAgeException extends Exception{
    public NoSuchAgeException(String errMsg,double age){
        super("The error message is "+errMsg);
        System.err.println("a person' age can not be "+age);
    }
}
