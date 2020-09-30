package com.ucll.afstudeer.IoT.exception.game;

public class GameDoesNotExistException extends RuntimeException {

    public GameDoesNotExistException(){
        super();
    }

    public GameDoesNotExistException(String message){
        super(message);
    }

    public GameDoesNotExistException(String message, Throwable throwable){
        super(message, throwable);
    }
}
