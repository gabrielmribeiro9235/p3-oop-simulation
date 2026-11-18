import controller.DeclaracaoController;
import database.DatabaseInitializer;
import model.TipoDespesa;

public class Principal {
    static void main() {
        DatabaseInitializer.init();
        DeclaracaoController controller = new DeclaracaoController();

        // Testando casos inválidos
        try {
            controller.iniciarSimulacao(47_000.30, 5_000.50);
            controller.iniciarSimulacao(98_980.92, 15_000.50);
        } catch (RuntimeException e) {
            System.err.println("Erro ao iniciar simulação: " + e.getMessage());
        }

        try {
            controller.encerrarSimulacao(); // Encerrando a simulação iniciada na linha 12
        } catch (RuntimeException e) {
            System.err.println("Erro ao encerrar simulação: " + e.getMessage());
        }

        try {
            controller.adicionarDespesa(TipoDespesa.EDUCACAO, 1, "teste", 1000, "00000", "especifico");
        } catch (RuntimeException e) {
            System.err.println("Erro ao adicionar despesa: " + e.getMessage());
        }

        try {
            controller.encerrarSimulacao();
        } catch (RuntimeException e) {
            System.err.println("Erro ao encerrar simulação: " + e.getMessage());
        }

        try {
            controller.verRelatorioDaSimulacao();
        } catch (RuntimeException e) {
            System.err.println("Erro ao gerar relatório: " + e.getMessage());
        }

        // Testando casos válidos
        try {
            System.out.println("-".repeat(100));
            System.out.println("Ganho tributável = R$ 22.847,88 e valor já pago = R$ 0,00 (pessoa isenta)");
            System.out.println("-".repeat(100));

            controller.iniciarSimulacao(22_847.88, 0.0);
            System.out.println("> Simulação iniciada");

            controller.verRelatorioDaSimulacao();
            controller.encerrarSimulacao();
            System.out.println("> Simulação encerrada");

            System.out.println("-".repeat(100));
            System.out.println("Ganho tributável = R$ 22.947,88 e valor já pago = R$ 0,00 (paga sobre R$ 100,00)");
            System.out.println("-".repeat(100));

            controller.iniciarSimulacao(22_947.88, 0.0);
            System.out.println("> Simulação iniciada");

            controller.verRelatorioDaSimulacao();
            controller.encerrarSimulacao();
            System.out.println("> Simulação encerrada");

            System.out.println("-".repeat(100));
            System.out.println("Ganho tributável = R$ 35.000,00 e valor já pago = R$ 500,00 (cálculo por faixas: 7,5% + 15%)");
            System.out.println("-".repeat(100));

            controller.iniciarSimulacao(35_000.00, 500.00);
            System.out.println("> Simulação iniciada");

            controller.verRelatorioDaSimulacao();
            controller.encerrarSimulacao();
            System.out.println("> Simulação encerrada");

            System.out.println("-".repeat(100));
            System.out.println("Ganho tributável = R$ 25.000,00 e valor já pago = R$ 500,00, com R$ 1.500,00 de despesa");
            System.out.println("com educação e R$ 2.000,00 com saúde (esta limitada a R$ 1.500,00). Deve restituir nos dois modelos.");

            controller.iniciarSimulacao(25_000.00, 500.00);
            System.out.println("> Simulação iniciada");

            controller.adicionarDespesa(TipoDespesa.EDUCACAO, 1, "Mensalidade escolar", 1_500.00, "12.345.678/0001-88", "Colégio Escola");
            controller.adicionarDespesa(TipoDespesa.SAUDE, 2, "Consulta médica", 2_000.00, "48.501.350/0001-67", "CRM 100001");

            controller.verRelatorioDaSimulacao();
            controller.encerrarSimulacao();
            System.out.println("> Simulação encerrada");

            System.out.println("-".repeat(100));
            System.out.println("Ganho tributável = R$ 1.000.000,00 e valor já pago = R$ 100.000,00, com R$ 15.000,00 de despesa");
            System.out.println("com educação e R$ 100.000,00 com saúde (em 10 despesas separadas, ambas limitadas ao teto)");
            System.out.println("Essa pessoa paga imposto nos dois modelos (entra na última faixa da declaração completa).");

            controller.iniciarSimulacao(1_000_000.00, 100_000.00);
            System.out.println("> Simulação iniciada");

            controller.adicionarDespesa(TipoDespesa.EDUCACAO, 1, "Escola dos filhos", 15_000.00, "00.579.870/0001-24", "Colégio Escola");

            for (int i = 2; i < 12; i++) {
                controller.adicionarDespesa(TipoDespesa.SAUDE, i, "Despesa com saúde " + i, 10_000.00, "99.219.540/0001-25", "CRM 200002");
            }

            controller.verRelatorioDaSimulacao();
            controller.encerrarSimulacao();
            System.out.println("> Simulação encerrada");

        } catch (RuntimeException e) {
            System.err.println("Erro: " + e.getMessage());
        }
    }
}
