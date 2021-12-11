package pw.ewen.WLPT.services.resources;

import java.util.List;
import java.util.Optional;

/**
 * created by wenliang on 2021/12/9
 */
public interface ResourceServiceFunction<T> {
    List<T> findAll();
    List<T> findAll(String filter);
    Optional<T> findOne(long id);

    T add(T t);
    void update(T t);
    void delete(T t);
}
