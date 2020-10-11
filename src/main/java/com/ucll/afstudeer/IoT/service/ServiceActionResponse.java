package com.ucll.afstudeer.IoT.service;

import com.ucll.afstudeer.IoT.domain.constant.ServiceError;

// response from a service action
// Call Success() or Fail() for a simple boolean response indicating if the service action was a success or not
// Call the constructor if the service action holds a return value from the service (success), or null (fail)
public class ServiceActionResponse<T> {
    T value;
    ServiceError error;

    private ServiceActionResponse(T value, ServiceError error) {
        this.value = value;
        this.error  = error;
    }

    public ServiceActionResponse(T value) {
        this(value, ServiceError.None);
    }

    public ServiceActionResponse(ServiceError error) {
        this(null, error);
    }

    public T getValue() {
        return value;
    }

    public ServiceError getError() {
        return error;
    }

    public static ServiceActionResponse<Boolean> Fail(ServiceError error) {
        return new ServiceActionResponse<>(false, error);
    }

    public static ServiceActionResponse<Boolean> Success() {
        return new ServiceActionResponse<>(true, ServiceError.None);
    }
}
