package persistence.mapper;

import model.Despesa;
import model.DespesaEducacao;
import persistence.dto.DespesaEducacaoDTO;

import java.util.Objects;

public class DespesaEducacaoMapper {
    private DespesaEducacaoMapper() {}

    public static Despesa fromDto(DespesaEducacaoDTO dto) {
        Objects.requireNonNull(dto, "dto não pode ser nulo.");

        return new DespesaEducacao(dto.id(), dto.descricao(), dto.valor(), dto.cnpj(), dto.nomeInstituicao());
    }

    public static DespesaEducacaoDTO toDto(DespesaEducacao despesa, int idDeclaracao) {
        Objects.requireNonNull(despesa, "despesa não pode ser nula.");

        return new DespesaEducacaoDTO(
                despesa.getId(),
                despesa.getDescricao(),
                despesa.getValor(),
                despesa.getCnpj(),
                despesa.getNomeInstituicao(),
                idDeclaracao
        );
    }
}
