public class Veterinario {
    private String nome;
    private String cpf;
    private String especialidade;
    private String telefone;

    public Veterinario(String nome, String cpf, String especialidade, String telefone) {
        this.telefone = telefone;
        this.cpf = cpf;
        this.nome = nome;
        this.especialidade = especialidade;
    }
    public String getTelefone() { return telefone; }
    public String getEspecialidade() { return especialidade; }
    public String getNome() { return nome; }
    @Override
    public String toString() {
        return String.format("%s | CPF: %s | Especialidade: %s | Tel: %s", nome, cpf, especialidade, telefone);
    }
    public String getCpf() { return cpf; }
}
