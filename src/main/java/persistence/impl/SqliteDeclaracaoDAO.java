package persistence.impl;

import config.ConnectionFactory;
import persistence.dao.DeclaracaoDAO;
import persistence.dto.DeclaracaoDTO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Optional;

public class SqliteDeclaracaoDAO implements DeclaracaoDAO {
    @Override
    public void save(DeclaracaoDTO declaracaoDto) {
        Objects.requireNonNull(declaracaoDto, "declaracaoDto não pode ser nulo.");

        String sql = "INSERT INTO declaracao (id, ganho_tributavel, valor_ja_pago) VALUES (?, ?, ?)";

        try (PreparedStatement statement = ConnectionFactory.getPreparedStatement(sql)) {
            statement.setInt(1, declaracaoDto.id());
            statement.setDouble(2, declaracaoDto.ganhoTributavel());
            statement.setDouble(3, declaracaoDto.valorJaPago());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir a declaração: " + declaracaoDto.id(), e);
        }
    }

    @Override
    public void update(DeclaracaoDTO declaracaoDto) {
        Objects.requireNonNull(declaracaoDto, "declaracaoDto não pode ser nulo.");

        String sql = "UPDATE declaracao SET ganho_tributavel = ?, valor_ja_pago = ? WHERE id = ?";

        try (PreparedStatement statement = ConnectionFactory.getPreparedStatement(sql)) {
            statement.setDouble(1, declaracaoDto.ganhoTributavel());
            statement.setDouble(2, declaracaoDto.valorJaPago());
            statement.setInt(3, declaracaoDto.id());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar a declaracao: " + declaracaoDto.id(), e);
        }
    }

    @Override
    public void delete(DeclaracaoDTO declaracaoDto) {
        Objects.requireNonNull(declaracaoDto, "declaracaoDto não pode ser nulo.");

        String sql = "DELETE FROM declaracao WHERE id = ?";

        try (PreparedStatement statement = ConnectionFactory.getPreparedStatement(sql)) {
            statement.setInt(1, declaracaoDto.id());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar a declaracao: " + declaracaoDto.id(), e);
        }
    }

    @Override
    public Optional<DeclaracaoDTO> findById(int id) {
        String sql = "SELECT * FROM declaracao WHERE id = ?";

        try (PreparedStatement statement = ConnectionFactory.getPreparedStatement(sql)) {
            statement.setInt(1, id);

            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                return  Optional.of(new DeclaracaoDTO(
                        rs.getInt("id"),
                        rs.getDouble("ganho_tributavel"),
                        rs.getDouble("valor_ja_pago")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar a declaracao: " + id, e);
        }

        return Optional.empty();
    }
}
