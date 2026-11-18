package persistence.dao;

import persistence.dto.DespesaSaudeDTO;

import java.util.List;

public interface DespesaSaudeDAO {
    void adicinarDespesa(DespesaSaudeDTO despesaSaudeDTO);
    void removerDespesa(DespesaSaudeDTO despesaSaudeDTO);
    List<DespesaSaudeDTO> findAllByIdDeclaracao(int idDeclaracao);
}
