package core.exceptions.device;

public class InvalidDeviceCreationInputException extends RuntimeException {

    public InvalidDeviceCreationInputException(){
        super();
    }

    public InvalidDeviceCreationInputException(String message){
        super(message);
    }

    public InvalidDeviceCreationInputException(String message, Throwable throwable){
        super(message, throwable);
    }
}
