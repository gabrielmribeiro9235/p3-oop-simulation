package persistence.impl;

import config.ConnectionFactory;
import persistence.dao.DespesaEducacaoDAO;
import persistence.dto.DespesaEducacaoDTO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SqliteDespesaEducacaoDAO implements DespesaEducacaoDAO {
    @Override
    public void adicinarDespesa(DespesaEducacaoDTO despesaEducacaoDTO) {
        Objects.requireNonNull(despesaEducacaoDTO,  "despesaEducacaoDTO nao pode ser nulo.");

        String sql = """
                INSERT INTO despesa_educacao (id, descricao, valor, cnpj, nome_instituicao, id_declaracao)
                    VALUES (?, ?, ?, ?, ?, ?)
                """;
        try (PreparedStatement statement = ConnectionFactory.getPreparedStatement(sql)) {
            statement.setInt(1, despesaEducacaoDTO.id());
            statement.setString(2, despesaEducacaoDTO.descricao());
            statement.setDouble(3, despesaEducacaoDTO.valor());
            statement.setString(4, despesaEducacaoDTO.cnpj());
            statement.setString(5, despesaEducacaoDTO.nomeInstituicao());
            statement.setInt(6, despesaEducacaoDTO.idDeclaracao());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao cadastrar a despesa: " + despesaEducacaoDTO.id(), e);
        }
    }

    @Override
    public void removerDespesa(DespesaEducacaoDTO despesaEducacaoDTO) {
        Objects.requireNonNull(despesaEducacaoDTO,  "despesaEducacaoDTO nao pode ser nulo.");

        String sql = "DELETE FROM despesa_saude WHERE id = ?";

        try (PreparedStatement statement = ConnectionFactory.getPreparedStatement(sql)) {
            statement.setInt(1, despesaEducacaoDTO.id());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao remover a  despesa: " + despesaEducacaoDTO.id(), e);
        }
    }

    @Override
    public List<DespesaEducacaoDTO> findAllByIdDeclaracao(int idDeclaracao) {
        String sql = "SELECT * FROM despesa_educacao WHERE id_declaracao = ?";

        try (PreparedStatement statement = ConnectionFactory.getPreparedStatement(sql)) {
            statement.setInt(1, idDeclaracao);

            ResultSet rs = statement.executeQuery();
            List<DespesaEducacaoDTO> despesasComEducacao = new ArrayList<>();

            while (rs.next()) {
                despesasComEducacao.add(new DespesaEducacaoDTO(
                        rs.getInt("id"),
                        rs.getString("descricao"),
                        rs.getDouble("valor"),
                        rs.getString("cnpj"),
                        rs.getString("nome_instituicao"),
                        rs.getInt("id_declaracao")
                ));
            }

            return despesasComEducacao;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar as despesas com saúde da declaração: " + idDeclaracao, e);
        }
    }
}
