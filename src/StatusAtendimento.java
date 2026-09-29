public enum StatusAtendimento {
    AGENDADO("Agendado"),
    EM_ANDAMENTO("Em andamento"),
    FINALIZADO("Finalizado");

    private final String descricao;

    StatusAtendimento(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
