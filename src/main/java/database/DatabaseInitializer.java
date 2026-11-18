package database;

import config.ConnectionFactory;

import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseInitializer {
    private DatabaseInitializer() {}

    public static void init() {
        System.out.println("Inicializando Database...");
        System.out.println(" > Criando a tabela declaracao");
        createDeclaracaoTable();
        System.out.println(" > Criando a tabela despesa_saude");
        createDespesaSaudeTable();
        System.out.println(" > Criando a tabela despesa_educacao");
        createDespesaEducacaoTable();
    }

    private static void createDeclaracaoTable() {
        String sql = """
                CREATE TABLE IF NOT EXISTS declaracao (
                    id                  INTEGER PRIMARY KEY,
                    ganho_tributavel    REAL NOT NULL CHECK(ganho_tributavel > 0),
                    valor_ja_pago       REAL NOT NULL  CHECK(valor_ja_pago > 0)
                );
                """;

        try (Statement statement = ConnectionFactory.getConnection().createStatement()) {
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao criar a tabela declaracao", e);
        }
    }

    private static void createDespesaSaudeTable() {
        String sql = """
                CREATE TABLE IF NOT EXISTS despesa_saude (
                    id                  INTEGER PRIMARY KEY,
                    descricao           TEXT,
                    valor               REAL NOT NULL CHECK(valor > 0),
                    cnpj                TEXT NOT NULL,
                    registro_conselho   TEXT NOT NULL,
                    id_declaracao       INTEGER NOT NULL,
                    FOREIGN KEY(id_declaracao) REFERENCES declaracao(id)
                        ON DELETE CASCADE
                );
                """;

        try (Statement statement = ConnectionFactory.getConnection().createStatement()) {
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao criar a tabela despesa_saude", e);
        }
    }

    private static void createDespesaEducacaoTable() {
        String sql = """
                CREATE TABLE IF NOT EXISTS despesa_educacao (
                    id                  INTEGER PRIMARY KEY,
                    descricao           TEXT,
                    valor               REAL NOT NULL CHECK(valor > 0),
                    cnpj                TEXT NOT NULL,
                    nome_instituicao    TEXT NOT NULL,
                    id_declaracao       INTEGER NOT NULL,
                    FOREIGN KEY(id_declaracao) REFERENCES declaracao(id)
                        ON DELETE CASCADE
                );
                """;

        try (Statement statement = ConnectionFactory.getConnection().createStatement()) {
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao criar a tabela despesa_educacao", e);
        }
    }
}
