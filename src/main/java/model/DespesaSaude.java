package model;

public class DespesaSaude extends Despesa {
    public final static double DEDUCAO_MAX = 1_500.0;

    private final String registroConselho;

    public DespesaSaude(int id, String descricao, double valor, String cnpj, String registroConselho) {
        super(id, descricao, valor, cnpj);
        this.registroConselho = registroConselho;
    }

    public String getRegistroConselho() {
        return registroConselho;
    }

    @Override
    public String toString() {
        return super.toString() + " | registro conselho: " + registroConselho;
    }
}
