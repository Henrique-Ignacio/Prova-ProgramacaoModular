import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final Scanner SC = new Scanner(System.in);
    private static final DateTimeFormatter FMT_DATA = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final DateTimeFormatter FMT_HORA = DateTimeFormatter.ofPattern("HH:mm");

    public static void main(String[] args) {
        Clinica clinica = new Clinica();
        InicioPrograma(clinica);

        int escolha;
        do {

            exibirMenu();
            escolha = lerInt("Escolha uma opção: ");

            try {
                executarEscolha(escolha, clinica);
            } 
            catch (IllegalArgumentException | IllegalStateException e) {
                System.out.println("Erro: " + e.getMessage());
            }

        } 
        while (escolha != 0);
        System.out.println("Sistema encerrado.");
    }

    private static void InicioPrograma(Clinica clinica) {
        clinica.addVeterinario(new Veterinario("Dr. Henrique Ignacio", "434.164.651.62", "Clínica geral", "(31) 91111-1111"));
        clinica.addVeterinario(new Veterinario("Dra. Marina Lopes", "665.354.914.57", "Cirurgia", "(31) 92222-2222"));
        clinica.addVeterinario(new Veterinario("Dr. João Pedro", "879.516.535.32", "Dermatologia", "(31) 93333-3333"));

        clinica.addSala(new Sala(1, "A", 2, "Cirurgia"));
        clinica.addSala(new Sala(2, "B", 1, "Cirurgia"));
        clinica.addSala(new Sala(3, "C", 1, "Consultorio"));
    }

    private static void exibirMenu() {
        System.out.println();
        System.out.println("***** CLiNICA VETERINaRIA *****");
        System.out.println("1 - Cadastrar atendimento");
        System.out.println("2 - Colocar veterinario em uma sala");
        System.out.println("3 - Colocar atendimento em uma sala");
        System.out.println("4 - Exibir atendimentos da sala");
        System.out.println("5 - Total de finalizados por sala");
        System.out.println("6 - Buscar por status");
        System.out.println("7 - Detalhes de um atendimento");
        System.out.println("0 - Exit");
    }

    private static void executarEscolha(int escolha, Clinica clinica) {
        switch (escolha) {
            case 1: 
                addAtendimento(clinica); 
                break;
            case 2: 
                associarVeterinario(clinica); 
                break;
            case 3: 
                atribuirAtendimento(clinica); 
                break;
            case 4: 
                exibirAtendimentosDaSala(clinica); 
                break;
            case 5: 
                exibirFinalizadosPorSala(clinica); 
                break;
            case 6: 
                getByStatus(clinica); 
                break;
            case 7: 
                detalhes(clinica); 
                break;
            case 0: 
                break;
            default: 
                System.out.println("Opção inválida.");
        }
    }

    private static void addAtendimento(Clinica clinica) {
        System.out.println("*** Cadastro de atendimento ***");
        String animal = ler("Nome do animal: ");
        String tutor = ler("Nome do tutor: ");
        String especie = ler("Especie: ");
        LocalDate data = lerDate("Data (dd/MM/yyyy): ");
        LocalTime horario = lerHrs("Horario (HH:mm): ");
        String observacoes = ler("Observações: ");
        StatusAtendimento status = lerStatus();

        System.out.println("*** Procedimento ***");
        String nomeProc = ler("Nome do procedimento: ");
        double valor = lerDouble("Preço (R$): ");
        int duracao = lerInt("Duração(minutos): ");
        NivelComplexidade nivel = lerNivel();

        Procedimento procedimento = new Procedimento(nomeProc, duracao, valor, nivel);
        Atendimento a = clinica.addAtendimento(animal, especie, tutor, data, horario, status, observacoes, procedimento);
        System.out.println("Atendimento cadastrado - Codigo: " + a.getCodigo());
    }

    private static void associarVeterinario(Clinica clinica) {
        System.out.println("*** Salas ***");
        for (Sala s : clinica.getSalas()) {
            System.out.println(s);
        }
        int numeroSala = lerInt("Número da sala: ");

        System.out.println("*** veterinarios ***");
        for (Veterinario v : clinica.getVeterinarios()) {
            System.out.println(v);
        }
        String cpf = ler("CPF do veterinario: ");

        clinica.associarVeterinarioASala(numeroSala, cpf);
        System.out.println("veterinario associado com sucesso.");
    }

    private static NivelComplexidade lerNivel() {
        NivelComplexidade[] valores = NivelComplexidade.values();
        while (true) {
            for (int i = 0; i < valores.length; i++) {
                System.out.println((i + 1) + " - " + valores[i].getDescricao());
            }
            int op = lerInt("Nível de complexidade: ");
            if (op >= 1 && op <= valores.length) {
                return valores[op - 1];
            }
            System.out.println("Opção inválida.");
        }
    }

    
    private static void exibirAtendimentosDaSala(Clinica clinica) {
        int numeroSala = lerInt("Número da sala: ");
        List<Atendimento> lista = clinica.listAtendimentosDaSala(numeroSala);
        for (Atendimento a : lista) {
            a.resumo();
        }
        System.out.println("Total de atendimentos - sala " + numeroSala + ": " + lista.size());
    }

    private static void exibirFinalizadosPorSala(Clinica clinica) {
        for (Sala s : clinica.getSalas()) {
            System.out.println("Sala " + s.getNumero() + ": " + clinica.contFinalizadosDaSala(s) + " finalizado(s)");
        }
    }



    private static void detalhes(Clinica clinica) {
        int codigo = lerInt("Codigo do atendimento: ");
        clinica.searchAtendimento(codigo).detalhes();
    }

    private static void atribuirAtendimento(Clinica clinica) {
        int codigo = lerInt("Codigo do atendimento: ");
        int numeroSala = lerInt("Número da sala: ");
        clinica.atribuirAtendimentoASala(codigo, numeroSala);
        System.out.println("Atendimento atribuído à sala com sucesso.");
    }

    private static void getByStatus(Clinica clinica) {
        StatusAtendimento status = lerStatus();
        List<Atendimento> lista = clinica.getByStatus(status);
        if (lista.isEmpty()) {
            System.out.println("Nenhum atendimento com status " + status.getDescricao() + ".");
            return;
        }
        for (Atendimento a : lista) {
            a.detalhes();
        }
    }

    private static String ler(String mensagem) {
        System.out.print(mensagem);
        return SC.nextLine().trim();
    }

    private static int lerInt(String mensagem) {
        while (true) {
            try {
                return Integer.parseInt(ler(mensagem));
            } catch (NumberFormatException e) {
                System.out.println("Digite um número inteiro válido.");
            }
        }
    }

    private static double lerDouble(String mensagem) {
        while (true) {
            try {
                return Double.parseDouble(ler(mensagem).replace(",", "."));
            } catch (NumberFormatException e) {
                System.out.println("Digite um valor numérico válido.");
            }
        }
    }

    private static LocalDate lerDate(String mensagem) {
        while (true) {
            try {
                return LocalDate.parse(ler(mensagem), FMT_DATA);
            } catch (DateTimeParseException e) {
                System.out.println("Data inválida. Use o formato dd/MM/yyyy.");
            }
        }
    }

    private static LocalTime lerHrs(String mensagem) {
        while (true) {
            try {
                return LocalTime.parse(ler(mensagem), FMT_HORA);
            } catch (DateTimeParseException e) {
                System.out.println("Horário inválido. Use o formato HH:mm.");
            }
        }
    }

    private static StatusAtendimento lerStatus() {
        StatusAtendimento[] valores = StatusAtendimento.values();
        while (true) {
            for (int i = 0; i < valores.length; i++) {
                System.out.println((i + 1) + " - " + valores[i].getDescricao());
            }
            int op = lerInt("Status: ");
            if (op >= 1 && op <= valores.length) {
                return valores[op - 1];
            }
            System.out.println("Opção inválida.");
        }
    }
}
