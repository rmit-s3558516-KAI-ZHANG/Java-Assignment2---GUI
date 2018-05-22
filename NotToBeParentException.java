package OwnException;

public class NotToBeParentException extends Exception{
    public NotToBeParentException(String errMsg){
        super("The error message is "+errMsg);
        System.err.println("a child can not be one of parent");
    }
}
