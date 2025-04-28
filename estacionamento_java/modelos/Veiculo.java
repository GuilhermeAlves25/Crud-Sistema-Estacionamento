
package modelos;

public class Veiculo {
    public int id;
    public String placa;
    public String modelo;
    public String cor;
    public int idCliente;

    public Veiculo(int id, String placa, String modelo, String cor, int idCliente) {
        this.id = id;
        this.placa = placa;
        this.modelo = modelo;
        this.cor = cor;
        this.idCliente = idCliente;
    }

    @Override
    public String toString() {
        return id + " - " + placa + " | " + modelo + " | " + cor + " | Cliente ID: " + idCliente;
    }

    public Object getPlaca() {
        return placa;
    }
}
