package OwnException;

public class NotToBeColleaguesException extends Exception{
    public NotToBeColleaguesException(String errMsg,double age){
        super("The error message is "+errMsg);
        System.err.println("adding "+age+" years old person into colleague relation failed");
    }
}
