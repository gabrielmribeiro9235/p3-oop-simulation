package persistence.dto;

public record DespesaEducacaoDTO(
        int id,
        String descricao,
        double valor,
        String cnpj,
        String nomeInstituicao,
        int idDeclaracao
) {}
