package controller;

import model.TipoDespesa;
import persistence.impl.SqliteDeclaracaoDAO;
import persistence.impl.SqliteDespesaEducacaoDAO;
import persistence.impl.SqliteDespesaSaudeDAO;
import service.AdicionarDespesaService;
import service.ExcluirSimulacaoService;
import service.NovaSimulacaoService;
import service.RelatorioSimulacaoService;

public class DeclaracaoController {
    private final NovaSimulacaoService novaSimulacaoService;
    private final AdicionarDespesaService adicionarDespesaService;
    private final ExcluirSimulacaoService excluirSimulacaoService;
    private final RelatorioSimulacaoService relatorioSimulacaoService;

    public DeclaracaoController() {
        novaSimulacaoService = new NovaSimulacaoService(new SqliteDeclaracaoDAO());
        adicionarDespesaService = new AdicionarDespesaService(
                new SqliteDeclaracaoDAO(),
                new SqliteDespesaSaudeDAO(),
                new SqliteDespesaEducacaoDAO()
        );
        excluirSimulacaoService = new ExcluirSimulacaoService(new SqliteDeclaracaoDAO());
        relatorioSimulacaoService = new RelatorioSimulacaoService(
                new SqliteDeclaracaoDAO(),
                new SqliteDespesaSaudeDAO(),
                new SqliteDespesaEducacaoDAO()
        );
    }

    public void iniciarSimulacao(double ganhoTributavel, double valorJaPago) {
        novaSimulacaoService.iniciar(ganhoTributavel, valorJaPago);
    }

    public void adicionarDespesa(TipoDespesa tipo, int id, String descricao, double valor, String cnpj, String especifico) {
        adicionarDespesaService.adicionar(tipo, id, descricao, valor, cnpj, especifico);
    }

    public void encerrarSimulacao() {
        excluirSimulacaoService.excluir();
    }

    public void verRelatorioDaSimulacao() {
        System.out.println(relatorioSimulacaoService.criarRelatorio());
    }
}
