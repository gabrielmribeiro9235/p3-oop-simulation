package persistence.mapper;

import model.Declaracao;
import model.DeclaracaoCompleta;
import model.DeclaracaoSimplificada;
import persistence.dto.DeclaracaoDTO;

import java.util.Objects;

public class DeclaracaoMapper {
    private DeclaracaoMapper() {}

    public static DeclaracaoSimplificada simplificadaFromDto(DeclaracaoDTO dto) {
        Objects.requireNonNull(dto, "dto não pode ser nulo");

        return new DeclaracaoSimplificada(dto.id(), dto.ganhoTributavel(), dto.valorJaPago());
    }

    public static DeclaracaoCompleta completaFromDto(DeclaracaoDTO dto) {
        Objects.requireNonNull(dto, "dto não pode ser nulo");

        return new DeclaracaoCompleta(dto.id(), dto.ganhoTributavel(), dto.valorJaPago());
    }

    public static DeclaracaoDTO toDto(Declaracao declaracao) {
        Objects.requireNonNull(declaracao, "Declaracao não pode ser nula");

        return new DeclaracaoDTO(declaracao.getId(), declaracao.getGanhoTributavel(), declaracao.getValorJaPago());
    }
}
