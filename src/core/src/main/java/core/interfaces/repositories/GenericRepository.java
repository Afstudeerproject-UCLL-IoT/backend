package core.interfaces.repositories;

public interface GenericRepository<T> {
    boolean exists(T t);
}
