package OwnException;

public class NotToBeClassmatesException extends Exception{
    public NotToBeClassmatesException(String errMsg,double age){
        super("The error message is "+errMsg);
        System.err.println("adding "+age+" years old person into classmate relation failed");
    }
}
