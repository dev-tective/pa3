package gatodev.pa4web.services.generic;

import java.util.List;

public interface GenericService<T> {
    T add(T entity);
    T update(T entity);
    boolean delete(Integer id);
    T get(Integer id);
    List<T> getAll();
}
