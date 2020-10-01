package com.ucll.afstudeer.IoT.service;

public class ServiceActionResponse {
    boolean succeeded;
    String errorMessage;

    private ServiceActionResponse(boolean succeeded, String errorMessage) {
        this.succeeded = succeeded;
        this.errorMessage = errorMessage;
    }

    public boolean isSucceeded() {
        return succeeded;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    // instance methods
    public static ServiceActionResponse Success(){
        return new ServiceActionResponse(true, "Action succeeded");
    }

    public static ServiceActionResponse Fail(String message){
        return new ServiceActionResponse(false, message);
    }
}
