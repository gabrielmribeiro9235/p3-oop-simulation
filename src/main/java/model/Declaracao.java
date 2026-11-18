package model;

import java.util.Objects;

public abstract class Declaracao {
    private final int id;
    private final double ganhoTributavel;
    private final double valorJaPago;

    public Declaracao(int id, double ganhoTributavel, double valorJaPago) {
        this.id = id;
        this.ganhoTributavel = ganhoTributavel;
        this.valorJaPago = valorJaPago;
    }

    public final double valorAPagar() {
        return valorImposto() - valorJaPago;
    }

    protected abstract double valorImposto();

    public int getId() {
        return id;
    }

    public double getGanhoTributavel() {
        return ganhoTributavel;
    }

    public double getValorJaPago() {
        return valorJaPago;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Declaracao that = (Declaracao) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return String.format("""
                ID              : %d
                Ganho tributável: R$ %.2f
                Valor já pagao  : R$ %.2f
                """, id, ganhoTributavel, valorJaPago);
    }
}
