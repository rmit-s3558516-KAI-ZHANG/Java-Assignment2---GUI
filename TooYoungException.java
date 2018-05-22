package OwnException;

public class TooYoungException extends Exception{
    public TooYoungException(String errMsg,double age){
        super("The error message is "+errMsg);
        System.err.println("make friend with "+age+" years old child failed");
    }
}
