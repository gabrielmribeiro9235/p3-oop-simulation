package persistence.dto;

public record DespesaSaudeDTO(
        int id,
        String descricao,
        double valor,
        String cnpj,
        String registroConselho,
        int idDeclaracao
) {}
