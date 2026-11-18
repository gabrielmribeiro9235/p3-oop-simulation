package persistence.mapper;

import model.Despesa;
import model.DespesaSaude;
import persistence.dto.DespesaSaudeDTO;

import java.util.Objects;

public class DespesaSaudeMapper {
    private DespesaSaudeMapper() {}

    public static Despesa fromDto(DespesaSaudeDTO dto) {
        Objects.requireNonNull(dto, "dto não pode ser nulo.");

        return new DespesaSaude(dto.id(), dto.descricao(), dto.valor(), dto.cnpj(), dto.registroConselho());
    }

    public static DespesaSaudeDTO toDto(DespesaSaude despesa, int idDeclaracao) {
        Objects.requireNonNull(despesa, "despesa não pode ser nula.");

        return new DespesaSaudeDTO(
                despesa.getId(),
                despesa.getDescricao(),
                despesa.getValor(),
                despesa.getCnpj(),
                despesa.getRegistroConselho(),
                idDeclaracao
        );
    }
}
