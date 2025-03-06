package tn.esprit.powerHR.interfaces.EntrepriseDep;

import tn.esprit.powerHR.models.EntrepriseDep.*;
import java.util.List;

public interface IDepartement {
    void add(Departement departement);
    void update(Departement departement);
    void delete(int id);
    Departement getById(int id);
    List<Departement> getAll();
} 