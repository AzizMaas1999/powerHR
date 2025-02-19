package tn.esprit.powerHr.interfaces;

import tn.esprit.powerHr.entities.Departement;
import java.util.List;

public interface IDepartement {
    void add(Departement departement);
    void update(Departement departement);
    void delete(int id);
    Departement getById(int id);
    List<Departement> getAll();
} 