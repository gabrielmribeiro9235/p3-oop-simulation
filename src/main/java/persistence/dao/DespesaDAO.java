package persistence.dao;

import model.Despesa;
import model.TipoDespesa;

import java.util.List;

public interface DespesaDAO {
    void adicinarDespesa(TipoDespesa tipo, Despesa despesa, int idDeclaraco);
    void removerDespesa(TipoDespesa tipo, Despesa despesa);
    List<Despesa> findAllByIdDeclaracao(int idDeclaracao);
}
