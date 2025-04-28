package modelos;

public class Movimentacao {
    private int id;
    private int idVeiculo;
    private int idVaga;
    private String entrada;
    private String saida;

    public Movimentacao(int id, int idVeiculo, int idVaga, String entrada, String saida) {
        this.id = id;
        this.idVeiculo = idVeiculo;
        this.idVaga = idVaga;
        this.entrada = entrada;
        this.saida = saida;
    }

    // Getters e Setters
    public int getId() {
        return id;
    }

    public int getIdVeiculo() {
        return idVeiculo;
    }

    public int getIdVaga() {
        return idVaga;
    }

    public String getEntrada() {
        return entrada;
    }

    public String getSaida() {
        return saida;
    }

    @Override
    public String toString() {
        return "Movimentacao{" +
                "id=" + id +
                ", idVeiculo=" + idVeiculo +
                ", idVaga=" + idVaga +
                ", entrada='" + entrada + '\'' +
                ", saida='" + (saida != null ? saida : "Em aberto") + '\'' +
                '}';
    }
}