package OwnException;

public class NotToBeCoupledException extends Exception{
    public NotToBeCoupledException(String errMsg){
        super("The error message is "+errMsg);
        System.err.println("adding couples failed");
    }
}
