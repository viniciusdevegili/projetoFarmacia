import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class Medicamento {
    public String nome;
    public String tipo;
    public int qtdComprimidos;
    public String qtdMG;
    public LocalDateTime dataLembrete;
    public String horarioLembrete;
    public ArrayList<Lembrete> lembretes;

    public static ArrayList<Medicamento> medicamentos = new ArrayList<>();

    public Medicamento(String nome, String tipo, int qtdComprimidos, String qtdMG) {
        this.nome = nome;
        this.tipo = tipo;
        this.qtdComprimidos = qtdComprimidos;
        this.qtdMG = qtdMG;
        this.lembretes = new ArrayList<>();
    }

    public static void cadastrarMedicamento(String nome, String tipo, int qtdComprimidos, String qtdMG) {
        medicamentos.add(new Medicamento(nome, tipo, qtdComprimidos, qtdMG));
    }

    public static void listarMedicamentos() {
        for (Medicamento medicamento : medicamentos) {
            System.out.println("Medicamento: " + medicamento.nome);
            System.out.println("Tipo: " + medicamento.tipo);
            System.out.println("Quantidade de comprimidos: " + medicamento.qtdComprimidos);
            System.out.println("Quantidade de miligramas: " + medicamento.qtdMG);
            System.out.println();
        }
    }

    public void listarLembretes() {
        System.out.println("Lembretes para o medicamento " + this.nome + ":");

        if (this.lembretes.isEmpty()) {
            System.out.println("Nenhum lembrete cadastrado.");
        } else {
            for (Lembrete lembrete : this.lembretes) {
                System.out.println("Data: " + lembrete.data);
                System.out.println("Horário: " + lembrete.horario);
                System.out.println(lembrete.mensagem);
                System.out.println();
            }
        }
    }

    public static void excluirMedicamento(String nome) {
        medicamentos.removeIf(m -> m.nome.equalsIgnoreCase(nome));
    }

    public static void editarMedicamento(String nome, String tipo, int qtdComprimidos, String qtdMG) {
        for (Medicamento medicamento : medicamentos) {
            if (medicamento.nome.equalsIgnoreCase(nome)) {
                medicamento.tipo = tipo;
                medicamento.qtdComprimidos = qtdComprimidos;
                medicamento.qtdMG = qtdMG;
                return;
            }
        }
        System.out.println("Medicamento não encontrado.");
    }

    public static Medicamento buscarMedicamento(String nome) {
        for (Medicamento medicamento : medicamentos) {
            if (medicamento.nome.equalsIgnoreCase(nome)) {
                return medicamento;
            }
        }
        return null;
    }

    public void definirLembrete(LocalDateTime dataLembrete, String horarioLembrete) {
        this.dataLembrete = dataLembrete;
        this.horarioLembrete = horarioLembrete;

        // Agendar a tarefa do lembrete
        TimerTask lembreteTask = new TimerTask() {
            @Override
            public void run() {
                System.out.println("Lembrete: Tome o medicamento '" + nome + "' agora!");
            }
        };

        LocalDateTime lembreteDateTime = LocalDateTime.of(dataLembrete.toLocalDate(), LocalTime.parse(horarioLembrete));
        Timer lembreteTimer = new Timer();
        lembreteTimer.schedule(lembreteTask, Date.from(lembreteDateTime.atZone(ZoneId.systemDefault()).toInstant()));

        // Adicionar o lembrete à lista de lembretes do medicamento
        if (lembretes == null) {
            lembretes = new ArrayList<>();
        }
        lembretes.add(
                new Lembrete(dataLembrete.toString(), horarioLembrete, "Lembrete para o medicamento '" + nome + "'."));
    }
}
