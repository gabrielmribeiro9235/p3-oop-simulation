package persistence.dao;

import model.Declaracao;

import java.util.List;
import java.util.Optional;

public interface DeclaracaoDAO {
    void save(Declaracao declaracao);
    void update(Declaracao declaracao);
    void delete(Declaracao declaracao);
    Optional<Declaracao> findById(int id);
}
