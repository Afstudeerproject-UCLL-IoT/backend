package com.ucll.afstudeer.IoT.service;

// response from a service action
// Call Success() or Fail() for a simple boolean response indicating if the service action was a success or not
// Call the constructor if the service action holds a return value from the service (success), or null (fail)
public class ServiceActionResponse<T> {
    T value;
    String message;

    private ServiceActionResponse(T value, String message) {
        this.value = value;
        this.message = message;
    }

    public ServiceActionResponse(T value){
        this(value, "");
    }

    public ServiceActionResponse(String errorMessage){
        this(null, errorMessage);
    }

    public T getValue() {
        return value;
    }

    public String getMessage() {
        return message;
    }

    public static ServiceActionResponse<Boolean> Fail(String errorMessage){
        return new ServiceActionResponse<>(false, errorMessage);
    }

    public static ServiceActionResponse<Boolean> Success(){
        return new ServiceActionResponse<>(true, "");
    }
}
