import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class Gerenciar {
    private static Timer lembreteTimer = new Timer();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Usuario usuario = null;
        boolean sairPrograma = false;

        while (!sairPrograma) {
            if (usuario == null) {
                exibirMenuNaoLogado();
                int opcao = scanner.nextInt();

                switch (opcao) {
                    case 1:
                        usuario = Usuario.cadastrarNovoUsuario();
                        System.out.println("Cadastro bem-sucedido! Bem-vindo, " + usuario.getNome() + ".");
                        break;
                    case 2:
                        usuario = Usuario.fazerLogin();
                        if (usuario != null) {
                            System.out.println("Login bem-sucedido! Bem-vindo, " + usuario.getNome() + ".");
                        } else {
                            System.out.println("Credenciais inválidas. Tente novamente.");
                        }
                        break;
                    case 3:
                        System.out.println("Saindo do programa. Até mais!");
                        sairPrograma = true;
                        break;
                    default:
                        System.out.println("Opção inválida. Tente novamente.");
                        break;
                }
            } else {
                exibirMenuLogado();
                int opcao = scanner.nextInt();

                switch (opcao) {
                    case 1:
                        System.out.println("Cadastrar Medicamento");
                        scanner.nextLine(); // Consumir a quebra de linha
                        System.out.println("Digite o nome do medicamento: ");
                        String nomeMedicamento = scanner.nextLine();
                        System.out.println("Digite o tipo do medicamento: ");
                        String tipoMedicamento = scanner.nextLine();
                        System.out.println("Digite a quantidade de comprimidos: ");
                        int qtdComprimidos = scanner.nextInt();
                        scanner.nextLine(); // Consumir a quebra de linha
                        System.out.println("Digite a quantidade de miligramas por cápsula: ");
                        String qtdMG = scanner.nextLine();

                        Medicamento.cadastrarMedicamento(nomeMedicamento, tipoMedicamento, qtdComprimidos, qtdMG);
                        System.out.println("Medicamento cadastrado com sucesso!");
                        break;
                    case 2:
                        System.out.println("Alterar Medicamento");
                        scanner.nextLine(); // Consumir a quebra de linha
                        System.out.println("Digite o nome do medicamento que deseja alterar: ");
                        String nomeMedicamentoAlterar = scanner.nextLine().trim();
                        Medicamento medicamentoParaAlterar = buscarMedicamento(nomeMedicamentoAlterar);

                        if (medicamentoParaAlterar != null) {
                            // Solicitar novos dados
                            System.out.println("Digite o novo tipo do medicamento: ");
                            String novoTipo = scanner.nextLine();
                            System.out.println("Digite a nova quantidade de comprimidos: ");
                            int novaQtdComprimidos = Integer.parseInt(scanner.nextLine());
                            System.out.println("Digite a nova quantidade de miligramas por cápsula: ");
                            String novaQtdMG = scanner.nextLine();

                            // Atualizar o medicamento
                            Medicamento.editarMedicamento(nomeMedicamentoAlterar, novoTipo, novaQtdComprimidos,
                                    novaQtdMG);

                            System.out.println("Medicamento alterado com sucesso!");
                        } else {
                            System.out.println("Medicamento não encontrado.");
                        }
                        break;
                    case 3:
                        System.out.println("Excluir Medicamento");
                        scanner.nextLine(); // Consumir a quebra de linha
                        System.out.println("Digite o nome do medicamento que deseja excluir: ");
                        String nomeMedicamentoExcluir = scanner.nextLine().trim();
                        Medicamento medicamentoParaExcluir = buscarMedicamento(nomeMedicamentoExcluir);

                        if (medicamentoParaExcluir != null) {
                            Medicamento.excluirMedicamento(nomeMedicamentoExcluir);
                            System.out.println("Medicamento excluído com sucesso!");
                        } else {
                            System.out.println("Medicamento não encontrado.");
                        }
                        break;
                    case 4:
                        System.out.println("Listar Medicamentos");
                        scanner.nextLine(); // Consumir a quebra de linha
                        Medicamento.listarMedicamentos();
                        break;
                    case 5:
                        System.out.println("Definir Lembrete");
                        scanner.nextLine(); // Consumir a quebra de linha
                        System.out.println("Digite o nome do medicamento para definir lembrete: ");
                        String nomeMedicamentoLembrete = scanner.nextLine().trim();
                        Medicamento medicamentoParaLembrete = buscarMedicamento(nomeMedicamentoLembrete);

                        if (medicamentoParaLembrete != null) {
                            System.out.println("Digite a data do lembrete (formato YYYY-MM-DD): ");
                            String dataLembreteStr = scanner.nextLine();
                            LocalDateTime dataLembrete = LocalDateTime.parse(dataLembreteStr + "T00:00:00");

                            System.out.println("Digite o horário do lembrete (formato HH:mm): ");
                            String horarioLembrete = scanner.nextLine();

                            medicamentoParaLembrete.definirLembrete(dataLembrete, horarioLembrete);
                            System.out.println("Lembrete definido com sucesso!");
                        } else {
                            System.out.println("Medicamento não encontrado.");
                        }
                        break;
                    case 6:
                        System.out.println("Listar Lembretes");
                        scanner.nextLine(); // Consumir a quebra de linha
                        System.out.println("Digite o nome do medicamento para listar os lembretes: ");
                        nomeMedicamentoLembrete = scanner.nextLine().trim();
                        medicamentoParaLembrete = buscarMedicamento(nomeMedicamentoLembrete);

                        if (medicamentoParaLembrete != null) {
                            medicamentoParaLembrete.listarLembretes();
                        } else {
                            System.out.println("Medicamento não encontrado.");
                        }
                        break;
                    case 7:
                        usuario = null; // Desloga o usuário
                        System.out.println("Desconectado com sucesso!");
                        break;
                    case 8:

                    default:
                        System.out.println("Opção inválida. Tente novamente.");
                        break;
                }
            }
        }
    }

    private static void exibirMenuNaoLogado() {
        System.out.println("Menu principal:");
        System.out.println("1. Cadastrar");
        System.out.println("2. Login");
        System.out.println("3. Sair");
        System.out.print("Escolha uma opção: ");
    }

    private static void exibirMenuLogado() {
        System.out.println("Menu para usuários logados:");
        System.out.println("1. Cadastrar Medicamento");
        System.out.println("2. Alterar Medicamento");
        System.out.println("3. Excluir Medicamento");
        System.out.println("4. Listar Medicamentos");
        System.out.println("5. Definir Lembrete");
        System.out.println("6. Listar Lembretes");
        System.out.println("7. Desconectar");
        System.out.println("8. Sair");
        System.out.print("Escolha uma opção: ");
    }

    private static Medicamento buscarMedicamento(String nomeMedicamento) {
        for (Medicamento medicamento : Medicamento.medicamentos) {
            if (medicamento.nome.equalsIgnoreCase(nomeMedicamento)) {
                return medicamento;
            }
        }
        return null;
    }
}
