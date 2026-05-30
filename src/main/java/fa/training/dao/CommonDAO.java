package fa.training.dao;

import java.util.List;

public interface CommonDAO <T>{
    boolean save(T t);
    boolean update(T t);
    boolean delete(int id);
    List<T> findAll();
    T findById(int id);
}
