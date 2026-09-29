import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Atendimento {
    private static final DateTimeFormatter FMT_DATA = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final DateTimeFormatter FMT_HORA = DateTimeFormatter.ofPattern("HH:mm");

    private int codigo;
    private String nomeAnimal;
    private String especie;
    private String nomeTutor;
    private LocalDate data;
    private LocalTime horario;
    private StatusAtendimento status;
    private String observacoes;
    private Procedimento procedimento;
    private Sala sala; 

    public Atendimento(int codigo, String nomeAnimal, String especie, String nomeTutor, LocalDate data,
                       LocalTime horario, StatusAtendimento status, String observacoes, Procedimento procedimento) {
        this.codigo = codigo;
        this.nomeAnimal = nomeAnimal;
        this.especie = especie;
        this.nomeTutor = nomeTutor;
        this.data = data;
        this.horario = horario;
        this.status = status;
        this.observacoes = observacoes;
        this.procedimento = procedimento;
    }

    public int getCodigo() { return codigo; }
    public String getNomeAnimal() { return nomeAnimal; }
    public StatusAtendimento getStatus() { return status; }
    public Procedimento getProcedimento() { return procedimento; }
    public Sala getSala() { return sala; }

    void setSala(Sala sala) {
        this.sala = sala;
    }

    public void resumo() {
        System.out.printf("[%d] %s (%s) - Tutor: %s - %s às %s - %s%n",
                codigo, nomeAnimal, especie, nomeTutor, data.format(FMT_DATA), horario.format(FMT_HORA),
                status.getDescricao());
    }

    public void detalhes() {
        System.out.println("----------------------------------------");
        System.out.println("Codigo: " + codigo);
        System.out.println("Animal: " + nomeAnimal + " (" + especie + ")");
        System.out.println("Tutor: " + nomeTutor);
        System.out.println("Data/Horário: " + data.format(FMT_DATA) + " às " + horario.format(FMT_HORA));
        System.out.println("Status: " + status.getDescricao());
        System.out.println("Observações: " + observacoes);
        System.out.println("Procedimento: " + procedimento);
        if (sala == null) {
            System.out.println("Sala: (nenhuma atribuída)");
            System.out.println("veterinario: (nenhum)");
        } else {
            System.out.println("Sala: " + sala);
            Veterinario vet = sala.getVeterinarioResponsavel();
            System.out.println("veterinario: " + (vet == null ? "(nenhum)" : vet));
        }
        System.out.println("----------------------------------------");
    }
}
