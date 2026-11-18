package model;

public class DeclaracaoSimplificada extends Declaracao {
    private final static double ALIQUOTA = 0.2;
    private final static double LIMITE_ISENCAO = 22_847.88;

    public DeclaracaoSimplificada(int id, double ganhoTributavel, double valorJaPago) {
        super(id, ganhoTributavel, valorJaPago);
    }

    @Override
    public double valorImposto() {
        return Math.max((getGanhoTributavel() - LIMITE_ISENCAO) * ALIQUOTA, 0);
    }
}
