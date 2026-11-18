package service;

import model.*;
import persistence.dao.DeclaracaoDAO;
import persistence.dao.DespesaEducacaoDAO;
import persistence.dao.DespesaSaudeDAO;
import persistence.mapper.DeclaracaoMapper;
import persistence.mapper.DespesaEducacaoMapper;
import persistence.mapper.DespesaSaudeMapper;

import java.util.Objects;

public class AdicionarDespesaService {
    private final DeclaracaoDAO declaracaoDAO;
    private final DespesaSaudeDAO despesaSaudeDAO;
    private final DespesaEducacaoDAO despesaEducacaoDAO;

    public AdicionarDespesaService(DeclaracaoDAO declaracaoDAO, DespesaSaudeDAO despesaSaudeDAO, DespesaEducacaoDAO despesaEducacaoDAO) {
        this.declaracaoDAO = declaracaoDAO;
        this.despesaSaudeDAO = despesaSaudeDAO;
        this.despesaEducacaoDAO = despesaEducacaoDAO;
    }

    public void adicionar(TipoDespesa tipo, int id, String descricao, double valor, String cnpj, String especifico) {
        Objects.requireNonNull(tipo, "tipo não pode ser nulo");

        DeclaracaoCompleta declaracaoCompleta = DeclaracaoMapper.completaFromDto(
                declaracaoDAO.findById(1)
                        .orElseThrow(() -> new IllegalStateException("Não há simulação vigente"))
        );

        despesaSaudeDAO.findAllByIdDeclaracao(declaracaoCompleta.getId()).forEach(d ->
                declaracaoCompleta.adicionarDespesa(DespesaSaudeMapper.fromDto(d))
        );
        despesaEducacaoDAO.findAllByIdDeclaracao(declaracaoCompleta.getId()).forEach(d ->
                declaracaoCompleta.adicionarDespesa(DespesaEducacaoMapper.fromDto(d))
        );

        if (tipo.equals(TipoDespesa.SAUDE)) {
            DespesaSaude despesa =  new DespesaSaude(id, descricao, valor, cnpj, especifico);

            declaracaoCompleta.adicionarDespesa(despesa);
            despesaSaudeDAO.adicinarDespesa(DespesaSaudeMapper.toDto(despesa, declaracaoCompleta.getId()));
        } else {
            DespesaEducacao despesa = new DespesaEducacao(id, descricao, valor, cnpj, especifico);

            declaracaoCompleta.adicionarDespesa(despesa);
            despesaEducacaoDAO.adicinarDespesa(DespesaEducacaoMapper.toDto(despesa, declaracaoCompleta.getId()));
        }
    }
}
