package core.interfaces.repositories;

public interface GenericRepository<T> {
    T add(T t);
    boolean exists(T t);
    boolean remove(T t);
}
