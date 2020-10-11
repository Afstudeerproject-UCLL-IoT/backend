package com.ucll.afstudeer.IoT.persistence;

public interface GenericRepository<T, K> {
    /**
     * Get the entity T by it's identifier K
     *
     * @param k The identifier
     * @return The entity if it was found by it's identifier or null when it was not found
     */
    T get(K k);

    boolean exists(K k);
}
