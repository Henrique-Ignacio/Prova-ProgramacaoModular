public class Procedimento {
    private String nome;
    private int duracaoEstimadaMin;
    private double valor;
    private NivelComplexidade nivelComplexidade;

    public Procedimento(String nome, int duracaoEstimadaMin, double valor, NivelComplexidade nivelComplexidade) {
        this.nome = nome;
        this.duracaoEstimadaMin = duracaoEstimadaMin;
        this.valor = valor;
        this.nivelComplexidade = nivelComplexidade;
    }

    public String getNome() { return nome; }
    public int getDuracaoEstimadaMin() { return duracaoEstimadaMin; }
    public double getValor() { return valor; }
    public NivelComplexidade getNivelComplexidade() { return nivelComplexidade; }

    @Override
    public String toString() {
        return String.format("%s (%d min, R$ %.2f, complexidade %s)",
                nome, duracaoEstimadaMin, valor, nivelComplexidade.getDescricao());
    }
}
