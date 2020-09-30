package com.ucll.afstudeer.IoT.exception.device;

public class DeviceAlreadyExistsException extends RuntimeException {

    public DeviceAlreadyExistsException(){
        super();
    }

    public DeviceAlreadyExistsException(String message){
        super(message);
    }

    public DeviceAlreadyExistsException(String message, Throwable throwable){
        super(message, throwable);
    }
}
