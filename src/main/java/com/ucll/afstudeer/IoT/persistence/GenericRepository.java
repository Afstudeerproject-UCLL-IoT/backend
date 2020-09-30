package com.ucll.afstudeer.IoT.persistence;

public interface GenericRepository<T> {
    boolean isPresent(T t);
}
