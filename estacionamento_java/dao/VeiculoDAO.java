
package dao;

import modelos.Veiculo;
import java.sql.*;
import java.util.*;

public class VeiculoDAO {
    public void inserir(String placa, String modelo, String cor, int idCliente) {
        String sql = "INSERT INTO veiculos (placa, modelo, cor, id_cliente) VALUES (?, ?, ?, ?)";

        try (Connection conn = Conexao.obterConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, placa);
            stmt.setString(2, modelo);
            stmt.setString(3, cor);
            stmt.setInt(4, idCliente);
            stmt.executeUpdate();
            System.out.println("✅ Veículo cadastrado.");
        } catch (SQLException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    public List<Veiculo> listar() {
        List<Veiculo> lista = new ArrayList<>();
        String sql = "SELECT * FROM veiculos";

        try (Connection conn = Conexao.obterConexao();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                lista.add(new Veiculo(
                        rs.getInt("id"),
                        rs.getString("placa"),
                        rs.getString("modelo"),
                        rs.getString("cor"),
                        rs.getInt("id_cliente")
                ));
            }

        } catch (SQLException e) {
            System.out.println("Erro: " + e.getMessage());
        }

        return lista;
    }

    public Veiculo buscarPorId(int id) {
        String sql = "SELECT * FROM veiculos WHERE id = ?";

        try (Connection conn = Conexao.obterConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Veiculo(
                        rs.getInt("id"),
                        rs.getString("placa"),
                        rs.getString("modelo"),
                        rs.getString("cor"),
                        rs.getInt("id_cliente")
                );
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar veículo: " + e.getMessage());
        }
        return null;
    }
}
