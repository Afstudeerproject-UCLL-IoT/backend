package com.ucll.afstudeer.IoT.exception.game;

public class CannotBecomeFirstPuzzleDeviceOfGameException extends RuntimeException {

    public CannotBecomeFirstPuzzleDeviceOfGameException(){
        super();
    }

    public CannotBecomeFirstPuzzleDeviceOfGameException(String message){
        super(message);
    }

    public CannotBecomeFirstPuzzleDeviceOfGameException(String message, Throwable throwable){
        super(message, throwable);
    }
}
