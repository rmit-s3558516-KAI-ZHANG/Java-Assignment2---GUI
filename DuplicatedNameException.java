package OwnException;

public class DuplicatedNameException extends Exception{
    public DuplicatedNameException(String errMsg,String name){
        super("The error message is "+errMsg);
        System.err.println("already added the peron whose name is "+name);
    }
}
