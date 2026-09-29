import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Clinica {
    private final List<Veterinario> veterinarios = new ArrayList<>();
    private final List<Sala> salas = new ArrayList<>();
    private final List<Atendimento> atendimentos = new ArrayList<>();
    private int proximoCodigo = 1;

    public void addVeterinario(Veterinario veterinario) {
        veterinarios.add(veterinario);
    }

    public void addSala(Sala sala) {
        salas.add(sala);
    }

    public List<Veterinario> getVeterinarios() { return new ArrayList<>(veterinarios); }
    public List<Sala> getSalas() { return new ArrayList<>(salas); }

    public Atendimento addAtendimento(String nomeAnimal, String especie, String nomeTutor, LocalDate data,
                                            LocalTime horario, StatusAtendimento status, String observacoes,
                                            Procedimento procedimento) {
        Atendimento atendimento = new Atendimento(proximoCodigo++, nomeAnimal, especie, nomeTutor, data, horario,
                status, observacoes, procedimento);
        atendimentos.add(atendimento);
        return atendimento;
    }

    public void associarVeterinarioASala(int numeroSala, String cpfVeterinario) {
        Sala sala = searchSala(numeroSala);
        Veterinario vet = searchVeterinario(cpfVeterinario);
        for (Sala outra : salas) {
            if (outra != sala && outra.getVeterinarioResponsavel() == vet) {
                throw new IllegalStateException("O veterinario " + vet.getNome()
                        + " já é responsável pela sala " + outra.getNumero() + ".");
            }
        }
        sala.setVeterinarioResponsavel(vet);
    }

    public void atribuirAtendimentoASala(int codigoAtendimento, int numeroSala) {
        Atendimento atendimento = searchAtendimento(codigoAtendimento);
        Sala sala = searchSala(numeroSala);
        sala.addAtendimento(atendimento);
    }

    public List<Atendimento> listAtendimentosDaSala(int numeroSala) {
        return new ArrayList<>(searchSala(numeroSala).getAtendimentos());
    }

    public int contFinalizadosDaSala(Sala sala) {
        return sala.countByStatus(StatusAtendimento.FINALIZADO);
    }

    public List<Atendimento> getByStatus(StatusAtendimento status) {
        List<Atendimento> resultado = new ArrayList<>();
        for (Atendimento a : atendimentos) {
            if (a.getStatus() == status) {
                resultado.add(a);
            }
        }
        return resultado;
    }

    public Sala searchSala(int numero) {
        for (Sala s : salas) {
            if (s.getNumero() == numero) {
                return s;
            }
        }
        throw new IllegalArgumentException("Sala " + numero + " não encontrada.");
    }

    public Veterinario searchVeterinario(String cpf) {
        for (Veterinario v : veterinarios) {
            if (v.getCpf().equals(cpf)) {
                return v;
            }
        }
        throw new IllegalArgumentException("veterinario com CPF " + cpf + " não encontrado.");
    }

    public Atendimento searchAtendimento(int codigo) {
        for (Atendimento a : atendimentos) {
            if (a.getCodigo() == codigo) {
                return a;
            }
        }
        throw new IllegalArgumentException("Atendimento " + codigo + " não encontrado.");
    }
}
