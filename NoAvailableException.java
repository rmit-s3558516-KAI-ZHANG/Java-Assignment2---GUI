package OwnException;

public class NoAvailableException extends Exception{
    public NoAvailableException(String errMsg){
        super("The error message is "+errMsg);
        System.err.println("adding couple failed");
    }
}
