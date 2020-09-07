package dao;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.Optional;

public interface Dao<T> {
    Collection<T> getAll();
    Optional<T> get(String id);
    void save(T t);
    void update(String id, T t) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException;
    void delete(String id);
}
