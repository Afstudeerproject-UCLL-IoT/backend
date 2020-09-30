package com.ucll.afstudeer.IoT.exception.subscribe;

public class DeviceCannotSubscribeToPuzzleException extends RuntimeException {

    public DeviceCannotSubscribeToPuzzleException(){
        super();
    }

    public DeviceCannotSubscribeToPuzzleException(String message){
        super(message);
    }

    public DeviceCannotSubscribeToPuzzleException(String message, Throwable throwable){
        super(message, throwable);
    }
}
