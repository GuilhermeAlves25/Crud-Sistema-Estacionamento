
package modelos;

public class Vaga {
    public int id;
    public int numero;
    public String tipo;
    public boolean ocupada;

    public Vaga(int id, int numero, String tipo, boolean ocupada) {
        this.id = id;
        this.numero = numero;
        this.tipo = tipo;
        this.ocupada = ocupada;
    }

    @Override
    public String toString() {
        String estado;
        if(ocupada){
            estado = "ocupada";
        }else{
            estado = "Disponivel";
        }
        return id + " - Nª Vaga " + numero + " | " + tipo + " | Estado: " + estado;
    }

    public Object getNumeroVaga() {
        return id;
    }
}
