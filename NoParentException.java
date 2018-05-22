package OwnException;

public class NoParentException extends Exception{
    public NoParentException(String errMsg){
        super("The error message is "+errMsg);
        System.err.println("a child try to added with no parent");
    }
}
