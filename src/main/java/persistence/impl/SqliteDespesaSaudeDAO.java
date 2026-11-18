package persistence.impl;

import config.ConnectionFactory;
import persistence.dao.DespesaSaudeDAO;
import persistence.dto.DespesaSaudeDTO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SqliteDespesaSaudeDAO implements DespesaSaudeDAO {
    @Override
    public void adicinarDespesa(DespesaSaudeDTO despesaSaudeDTO) {
        Objects.requireNonNull(despesaSaudeDTO, "despesaSaudeDTO nao pode ser nulo.");

        String sql = """
                INSERT INTO despesa_saude (id, descricao, valor, cnpj, registro_conselho, id_declaracao)
                    VALUES (?, ?, ?, ?, ?, ?)
                """;

        try (PreparedStatement statement = ConnectionFactory.getPreparedStatement(sql)) {
            statement.setInt(1, despesaSaudeDTO.id());
            statement.setString(2, despesaSaudeDTO.descricao());
            statement.setDouble(3, despesaSaudeDTO.valor());
            statement.setString(4, despesaSaudeDTO.cnpj());
            statement.setString(5, despesaSaudeDTO.registroConselho());
            statement.setInt(6, despesaSaudeDTO.idDeclaracao());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao cadastrar a despesa: " + despesaSaudeDTO.id(), e);
        }
    }

    @Override
    public void removerDespesa(DespesaSaudeDTO despesaSaudeDTO) {
        Objects.requireNonNull(despesaSaudeDTO,  "despesaSaudeDTO nao pode ser nulo.");

        String sql = "DELETE FROM despesa_saude WHERE id = ?";

        try (PreparedStatement statement = ConnectionFactory.getPreparedStatement(sql)) {
            statement.setInt(1, despesaSaudeDTO.id());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao remover a  despesa: " + despesaSaudeDTO.id(), e);
        }
    }

    @Override
    public List<DespesaSaudeDTO> findAllByIdDeclaracao(int idDeclaracao) {
        String sql = "SELECT * FROM despesa_saude WHERE id_declaracao = ?";

        try (PreparedStatement statement = ConnectionFactory.getPreparedStatement(sql)) {
            statement.setInt(1, idDeclaracao);

            ResultSet rs = statement.executeQuery();
            List<DespesaSaudeDTO> despesasComSaude = new ArrayList<>();

            while (rs.next()) {
                despesasComSaude.add(new DespesaSaudeDTO(
                        rs.getInt("id"),
                        rs.getString("descricao"),
                        rs.getDouble("valor"),
                        rs.getString("cnpj"),
                        rs.getString("registro_conselho"),
                        rs.getInt("id_declaracao")
                ));
            }

            return despesasComSaude;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar as despesas com saúde da declaração: " + idDeclaracao, e);
        }
    }
}
