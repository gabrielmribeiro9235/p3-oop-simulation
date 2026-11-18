package model;

public class DespesaEducacao extends Despesa {
    public final static double DEDUCAO_MAX = 2_000.0;

    private final String nomeInstituicao;

    public DespesaEducacao(int id, String descricao, double valor, String cnpj, String nomeInstituicao) {
        super(id, descricao, valor, cnpj);
        this.nomeInstituicao = nomeInstituicao;
    }

    public String getNomeInstituicao() {
        return nomeInstituicao;
    }

    @Override
    public String toString() {
        return "DespesaEducacao{" +
                "nomeInstituicao='" + nomeInstituicao + '\'' +
                "} " + super.toString();
    }
}
