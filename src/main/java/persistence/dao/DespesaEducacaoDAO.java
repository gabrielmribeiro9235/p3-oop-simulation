package persistence.dao;

import persistence.dto.DespesaEducacaoDTO;

import java.util.List;

public interface DespesaEducacaoDAO {
    void adicinarDespesa(DespesaEducacaoDTO despesaEducacaoDTO);
    void removerDespesa(DespesaEducacaoDTO despesaEducacaoDTO);
    List<DespesaEducacaoDTO> findAllByIdDeclaracao(int idDeclaracao);
}
