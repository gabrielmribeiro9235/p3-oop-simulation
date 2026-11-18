package service;

import model.DeclaracaoCompleta;
import model.DeclaracaoSimplificada;
import persistence.dao.DeclaracaoDAO;
import persistence.dao.DespesaEducacaoDAO;
import persistence.dao.DespesaSaudeDAO;
import persistence.mapper.DeclaracaoMapper;
import persistence.mapper.DespesaEducacaoMapper;
import persistence.mapper.DespesaSaudeMapper;

public class RelatorioSimulacaoService {
    private final DeclaracaoDAO declaracaoDAO;
    private final DespesaSaudeDAO despesaSaudeDAO;
    private final DespesaEducacaoDAO despesaEducacaoDAO;

    public RelatorioSimulacaoService(DeclaracaoDAO declaracaoDAO, DespesaSaudeDAO despesaSaudeDAO, DespesaEducacaoDAO despesaEducacoDAO) {
        this.declaracaoDAO = declaracaoDAO;
        this.despesaSaudeDAO = despesaSaudeDAO;
        this.despesaEducacaoDAO = despesaEducacoDAO;
    }

    public String criarRelatorio() {
        DeclaracaoCompleta completa = DeclaracaoMapper.completaFromDto(
                declaracaoDAO.findById(1)
                        .orElseThrow(() -> new IllegalStateException("Não há simulação vigente"))
        );
        DeclaracaoSimplificada simplificada = DeclaracaoMapper.simplificadaFromDto(
                declaracaoDAO.findById(2)
                        .orElseThrow(() -> new IllegalStateException("Não há simulação vigente"))
        );

        despesaSaudeDAO.findAllByIdDeclaracao(completa.getId()).forEach(d ->
                completa.adicionarDespesa(DespesaSaudeMapper.fromDto(d))
        );
        despesaEducacaoDAO.findAllByIdDeclaracao(completa.getId()).forEach(d ->
                completa.adicionarDespesa(DespesaEducacaoMapper.fromDto(d))
        );

        StringBuilder builder = new StringBuilder("-".repeat(100));
        builder.append("\nDeclaração Completa\n\n").append(completa)
                .append("\n")
                .repeat("-", 100)
                .append("\n")
                .append("Delcaração Simplificada\n\n")
                .append(simplificada)
                .append("\n")
                .repeat("-", 100)
                .append("\n");

        if (simplificada.valorAPagar() == completa.valorAPagar()) {
            builder.append("Em qualquer modelo de declaração o valor do imposto será igual\n");
        } else if (simplificada.valorAPagar() < completa.valorAPagar()) {
            builder.append("Vale mais a pena fazer a declaração simplificada\n")
                    .append(String.format("\nEconomia de R$ %.2f\n", completa.valorAPagar() - simplificada.valorAPagar()));
        } else {
            builder.append("Vale mais a pena fazer a declaração completa\n")
                    .append(String.format("\nEconomia de R$ %.2f\n", simplificada.valorAPagar() - completa.valorAPagar()));
        }

        return builder.toString();
    }
}
