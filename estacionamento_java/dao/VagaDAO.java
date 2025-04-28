package dao;

import modelos.Vaga;
import java.sql.*;
import java.util.*;

public class VagaDAO {
    public List<Vaga> listarDisponiveis() {  // Método mantido com este nome
        List<Vaga> lista = new ArrayList<>();
        String sql = "SELECT * FROM vagas WHERE ocupada = FALSE";

        try (Connection conn = Conexao.obterConexao();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                lista.add(new Vaga(
                        rs.getInt("id"),
                        rs.getInt("numero_vaga"),
                        rs.getString("tipo"),
                        rs.getBoolean("ocupada")
                ));
            }

        } catch (SQLException e) {
            System.out.println("Erro: " + e.getMessage());
        }

        return lista;
    }

    public void ocuparVaga(int idVaga, boolean ocupada) {
        String sql = "UPDATE vagas SET ocupada = ? WHERE id = ?";
        try (Connection conn = Conexao.obterConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setBoolean(1, ocupada);
            stmt.setInt(2, idVaga);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar vaga: " + e.getMessage());
        }
    }

    public void liberarVaga(int idVaga) {
        String sql = "UPDATE vagas SET ocupada = FALSE WHERE id = ?";
        try (Connection conn = Conexao.obterConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idVaga);
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Erro ao liberar vaga: " + e.getMessage());
        }
    }
    public boolean vagaDisponivel(int idVaga) {
        String sql = "SELECT ocupada FROM vagas WHERE id = ?";

        try (Connection conn = Conexao.obterConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idVaga);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return !rs.getBoolean("ocupada");
            }
        } catch (SQLException e) {
            System.out.println("Erro ao verificar vaga: " + e.getMessage());
        }
        return false;
    }

    public Vaga buscarPorId(int id) {
        String sql = "SELECT * FROM vagas WHERE id = ?";

        try (Connection conn = Conexao.obterConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Vaga(
                        rs.getInt("id"),
                        rs.getInt("numero_vaga"),
                        rs.getString("tipo"),
                        rs.getBoolean("ocupada")
                );
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar vaga: " + e.getMessage());
        }
        return null;
    }
}