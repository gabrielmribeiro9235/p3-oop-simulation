package persistence.dao;

import persistence.dto.DespesaDTO;
import model.TipoDespesa;

import java.util.List;

public interface DespesaDAO {
    void adicinarDespesa(TipoDespesa tipo, DespesaDTO despesaDTO);
    void removerDespesa(TipoDespesa tipo, DespesaDTO despesaDTO);
    List<DespesaDTO> findAllByIdDeclaracao(int idDeclaracao);
}
