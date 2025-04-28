
package modelos;

public class Cliente {
    public int id;
    public String nome;
    public String telefone;
    public String email;

    public Cliente(int id, String nome, String telefone, String email) {
        this.id = id;
        this.nome = nome;
        this.telefone = telefone;
        this.email = email;
    }

    @Override
    public String toString() {
        return id + " - " + nome + " | " + telefone + " | " + email;
    }
}
