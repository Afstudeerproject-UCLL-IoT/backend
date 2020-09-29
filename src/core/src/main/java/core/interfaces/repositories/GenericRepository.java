package core.interfaces.repositories;

public interface GenericRepository<T> {
    boolean isPresent(T t);
}
