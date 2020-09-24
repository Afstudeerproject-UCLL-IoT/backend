package core.exceptions.subscribe;

public class CannotSubscribeToItselfException extends RuntimeException {

    public CannotSubscribeToItselfException(){
        super();
    }

    public CannotSubscribeToItselfException(String message){
        super(message);
    }

    public CannotSubscribeToItselfException(String message, Throwable throwable){
        super(message, throwable);
    }
}
