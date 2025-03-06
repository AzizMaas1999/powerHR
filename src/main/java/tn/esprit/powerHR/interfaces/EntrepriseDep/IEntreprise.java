package tn.esprit.powerHR.interfaces.EntrepriseDep;

import tn.esprit.powerHR.models.EntrepriseDep.*;
import java.util.List;

public interface IEntreprise {
    void add(Entreprise entreprise) throws Exception;
    void update(Entreprise entreprise);
    void delete(int id);
    Entreprise getById(int id);
    List<Entreprise> getAll();
} 