package tn.esprit.powerHR.interfaces;

import java.util.List;

public interface IService<T> {
        void add(T t);          // Create
        void update(T t);       // Update
        void delete(T t);       // Delete
        List<T> getAll();       // Read (all)
}