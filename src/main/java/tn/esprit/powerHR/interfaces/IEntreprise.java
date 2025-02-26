package tn.esprit.powerHR.interfaces;

import tn.esprit.powerHR.models.Entreprise;
import java.util.List;

public interface IEntreprise {
    void add(Entreprise entreprise) throws Exception;
    void update(Entreprise entreprise);
    void delete(int id);
    Entreprise getById(int id);
    List<Entreprise> getAll();
} 