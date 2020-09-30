package com.ucll.afstudeer.IoT.exception.game;

public class GameAlreadyExistsException extends RuntimeException{

    public GameAlreadyExistsException(){
        super();
    }

    public GameAlreadyExistsException(String message){
        super(message);
    }

    public GameAlreadyExistsException(String message, Throwable throwable){
        super(message, throwable);
    }
}
