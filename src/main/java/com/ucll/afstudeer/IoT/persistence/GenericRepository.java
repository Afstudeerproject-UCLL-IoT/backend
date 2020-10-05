package com.ucll.afstudeer.IoT.persistence;

public interface GenericRepository<T, K> {
    T get(K k);

    boolean exists(K k);
}
