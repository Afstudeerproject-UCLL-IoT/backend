package core.exceptions.device;

public class InvalidDeviceNameException extends RuntimeException {

    public InvalidDeviceNameException(){
        super();
    }

    public InvalidDeviceNameException(String message){
        super(message);
    }

    public InvalidDeviceNameException(String message, Throwable throwable){
        super(message, throwable);
    }
}
