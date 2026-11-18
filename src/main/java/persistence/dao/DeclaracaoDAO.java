package persistence.dao;

import persistence.dto.DeclaracaoDTO;

import java.util.Optional;

public interface DeclaracaoDAO {
    void save(DeclaracaoDTO declaracaoDto);
    void update(DeclaracaoDTO declaracaoDto);
    void delete(DeclaracaoDTO declaracaoDto);
    Optional<DeclaracaoDTO> findById(int id);
}
