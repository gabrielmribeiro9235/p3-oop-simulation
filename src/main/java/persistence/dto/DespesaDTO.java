package persistence.dto;

public record DespesaDTO (
        int id,
        String descricao,
        double valor,
        String cnpj,
        String especificacao,
        int idDeclaracao
) {}
