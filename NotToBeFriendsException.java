package OwnException;

public class NotToBeFriendsException extends Exception{
    /**
     * This constructor is suitable for trying to connect two children with an age gap larger than 3 to be friends
     * @param errMsg the error message
     * @param age the age of connected child
     */
    public NotToBeFriendsException(String errMsg,double age){
        super("The error message is "+errMsg);
        System.err.println("make failed with "+age+" years old child failed, the age gap is larger than 3");
    }
/**
 * This constructor is suitable for trying to make an adult and a child friend
 * @param errMsg the error message
 */
    public NotToBeFriendsException(String errMsg){
        super("The error message is "+errMsg);
        System.err.println("make friends failed");
    }
}
