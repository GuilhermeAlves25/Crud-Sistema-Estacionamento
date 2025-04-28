package dao;

import modelos.Movimentacao;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class MovimentacaoDAO {
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public void registrarEntrada(int idVeiculo, int idVaga) {
        String sql = "INSERT INTO movimentacoes (id_veiculo, id_vaga, entrada) VALUES (?, ?, ?)";

        try (Connection conn = Conexao.obterConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idVeiculo);
            stmt.setInt(2, idVaga);
            stmt.setString(3, LocalDateTime.now().format(formatter));
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    public void registrarSaida(int idVeiculo) {
        String sql = "UPDATE movimentacoes SET saida = ? WHERE id_veiculo = ? AND saida IS NULL";

        try (Connection conn = Conexao.obterConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, LocalDateTime.now().format(formatter));
            stmt.setInt(2, idVeiculo);
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {

            } else {
                System.out.println("Nenhum veículo encontrado ou saída já registrada.");
            }
        } catch (SQLException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    public List<Movimentacao> listar() {
        List<Movimentacao> lista = new ArrayList<>();
        String sql = "SELECT * FROM movimentacoes";

        try (Connection conn = Conexao.obterConexao();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                lista.add(new Movimentacao(
                        rs.getInt("id"),
                        rs.getInt("id_veiculo"),
                        rs.getInt("id_vaga"),
                        rs.getString("entrada"),
                        rs.getString("saida")
                ));
            }

        } catch (SQLException e) {
            System.out.println("Erro: " + e.getMessage());
        }

        return lista;
    }
    public boolean veiculoEstacionado(int idVeiculo) {
        String sql = "SELECT COUNT(*) FROM movimentacoes WHERE id_veiculo = ? AND saida IS NULL";

        try (Connection conn = Conexao.obterConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idVeiculo);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            System.out.println("Erro ao verificar veículo estacionado: " + e.getMessage());
        }
        return false;
    }

    public List<Movimentacao> listarEstacionados() {
        List<Movimentacao> lista = new ArrayList<>();
        String sql = "SELECT * FROM movimentacoes WHERE saida IS NULL";

        try (Connection conn = Conexao.obterConexao();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                lista.add(new Movimentacao(
                        rs.getInt("id"),
                        rs.getInt("id_veiculo"),
                        rs.getInt("id_vaga"),
                        rs.getString("entrada"),
                        rs.getString("saida")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar veículos estacionados: " + e.getMessage());
        }
        return lista;
    }

    public int obterIdVagaPorVeiculo(int idVeiculo) {
        String sql = "SELECT id_vaga FROM movimentacoes WHERE id_veiculo = ? AND saida IS NULL";

        try (Connection conn = Conexao.obterConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idVeiculo);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("id_vaga");
            }
        } catch (SQLException e) {
            System.out.println("Erro ao obter vaga do veículo: " + e.getMessage());
        }
        return -1; // Retorna -1 se não encontrar
    }
}