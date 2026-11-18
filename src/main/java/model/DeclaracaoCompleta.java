package model;

import java.util.ArrayList;
import java.util.List;

public class DeclaracaoCompleta extends Declaracao {
    private final static double LIMITE_ISENCAO = 22_847.88;
    private final static double[] ALIQUOTAS = {0.075, 0.15, 0.225, 0.275};
    private final static double[][] FAIXAS = {
            {LIMITE_ISENCAO, 33_919.92},
            {33_919.93, 45_012.72},
            {45_012.73, 55_976.16},
            {55_976.17, Double.MAX_VALUE}
    };
    private final static double[] ACUMULADO = {
            0,
            (FAIXAS[0][1] - FAIXAS[0][0]) * ALIQUOTAS[0],
            (FAIXAS[0][1] - FAIXAS[0][0]) * ALIQUOTAS[0] + (FAIXAS[1][1] - FAIXAS[1][0]) * ALIQUOTAS[1],
            (FAIXAS[0][1] - FAIXAS[0][0]) * ALIQUOTAS[0] + (FAIXAS[1][1] - FAIXAS[1][0]) * ALIQUOTAS[1] + (FAIXAS[2][1] - FAIXAS[2][0]) * ALIQUOTAS[2],
    };

    private final List<Despesa> gastos;

    public DeclaracaoCompleta(int id, double ganhoTributavel, double valorJaPago) {
        super(id, ganhoTributavel, valorJaPago);

        gastos = new ArrayList<>();
    }

    @Override
    public double valorImposto() {
        double baseDeCalculo = getGanhoTributavel() - totalDespesasDedutiveis();

        if (baseDeCalculo < LIMITE_ISENCAO) {
            return 0;
        }

        for (int i = 0; i < FAIXAS.length - 1; i++) {
            if (baseDeCalculo < FAIXAS[i + 1][0]) {
                return (baseDeCalculo - FAIXAS[i][0]) * ALIQUOTAS[i] + ACUMULADO[i];
            }
        }

        return (baseDeCalculo - FAIXAS[3][0]) * ALIQUOTAS[3] + ACUMULADO[3];
    }

    public void adicionarDespesa(Despesa d) {
        if (d == null) {
            throw new IllegalArgumentException("Despesa não pode ser nula");
        }

        if (gastos.contains(d)) {
            throw new IllegalStateException("A despesa já foi cadastrada");
        }

        gastos.add(d);
    }

    public void removerDespesa(Despesa d) {
        if (d == null) {
            throw new IllegalArgumentException("Despesa não pode ser nula");
        }

        if (!gastos.contains(d)) {
            throw new IllegalStateException("A despesa não foi cadastrada");
        }

        gastos.remove(d);
    }

    private double totalDespesasDedutiveis() {
        return Math.min(somaDespesasSaude(), DespesaSaude.DEDUCAO_MAX)
                + Math.min(somaDespesasEducacao(), DespesaEducacao.DEDUCAO_MAX);
    }

    private double somaDespesasSaude() {
        return gastos.stream()
                .filter(d -> d instanceof DespesaSaude)
                .mapToDouble(Despesa::getValor)
                .sum();
    }

    private double somaDespesasEducacao() {
        return gastos.stream()
                .filter(d -> d instanceof DespesaEducacao)
                .mapToDouble(Despesa::getValor)
                .sum();
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder(super.toString());

        builder.append("\n\nDespesas com educação:\n");
        gastos.stream()
                .filter(d -> d instanceof DespesaEducacao)
                .forEach(d -> builder.append("    ").append(d).append("\n"));
        builder.append(String.format("Total dedutível com educação: R$ %.2f",
                Math.min(somaDespesasEducacao(), DespesaEducacao.DEDUCAO_MAX)));

        builder.append("\n\nDespesas com saúde:\n");
        gastos.stream()
                .filter(d -> d instanceof DespesaSaude)
                .forEach(d -> builder.append("    ").append(d).append("\n"));
        builder.append(String.format("Total dedutível com saúde: R$ %.2f",
                Math.min(somaDespesasSaude(), DespesaSaude.DEDUCAO_MAX)));

        builder.append(String.format("\n\nValor do imposto: R$ %.2f\n", valorImposto()))
                .append(String.format("Total deduzido  : R$ %.2f\n", totalDespesasDedutiveis()))
                .append(String.format("Valor a pagar   : R$ %.2f", valorAPagar()));

        return builder.toString();
    }
}
