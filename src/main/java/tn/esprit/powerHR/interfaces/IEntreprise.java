package tn.esprit.powerHr.interfaces;

import tn.esprit.powerHR.models.Entreprise;
import java.util.List;

public interface IEntreprise {
    void add(Entreprise entreprise);
    void update(Entreprise entreprise);
    void delete(int id);
    Entreprise getById(int id);
    List<Entreprise> getAll();
} 