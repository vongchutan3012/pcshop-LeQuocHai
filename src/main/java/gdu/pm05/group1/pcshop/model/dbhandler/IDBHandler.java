package gdu.pm05.group1.pcshop.model.dbhandler;

import java.util.List;

public interface IDBHandler {
    void save(Object... objects);
    void remove(Object... objects);
    <T> T get(Class<T> objClass, T target);
    <T> T get(Class<T> objClass, HQLParameter... parameters);
    <T> List<T> getAll(Class<T> objClass);
    void refresh(Object... objects);
}
