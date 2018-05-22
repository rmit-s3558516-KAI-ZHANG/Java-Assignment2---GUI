package OwnException;

public class InvalidNumberParentException extends Exception{
    public InvalidNumberParentException(String errMsg){
        super("The error message is "+errMsg);
        System.err.println("Operation failed");
    }
}
