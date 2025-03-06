package tn.esprit.powerHr.interfaces.EntrepriseDep;

import tn.esprit.powerHr.models.EntrepriseDep.Entreprise;
import java.util.List;

public interface IEntreprise {
    void add(Entreprise entreprise) throws Exception;
    void update(Entreprise entreprise);
    void delete(int id);
    Entreprise getById(int id);
    List<Entreprise> getAll();
} 