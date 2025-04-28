import dao.*;
import modelos.*;
import java.util.*;

public class Main {
    static Scanner scanner = new Scanner(System.in);
    static ClienteDAO clienteDAO = new ClienteDAO();
    static VeiculoDAO veiculoDAO = new VeiculoDAO();
    static VagaDAO vagaDAO = new VagaDAO();
    static MovimentacaoDAO movimentacaoDAO = new MovimentacaoDAO();

    public static void main(String[] args) {
        int opcao;
        do {
            System.out.println("\n=== MENU PRINCIPAL ===");
            System.out.println("1. Cadastrar Cliente");
            System.out.println("2. Listar Clientes");
            System.out.println("3. Cadastrar Veículo");
            System.out.println("4. Listar Veículos");
            System.out.println("5. Listar Vagas Disponíveis");
            System.out.println("6. Registrar Entrada");
            System.out.println("7. Registrar Saída");
            System.out.println("8. Listar Movimentações");
            System.out.println("0. Sair");
            System.out.print("Escolha: ");
            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1 -> cadastrarCliente();
                case 2 -> listarClientes();
                case 3 -> cadastrarVeiculo();
                case 4 -> listarVeiculos();
                case 5 -> listarVagas();
                case 6 -> registrarEntrada();
                case 7 -> registrarSaida();
                case 8 -> listarMovimentacoes();
                case 0 -> System.out.println("Encerrando...");
                default -> System.out.println("Opção inválida.");
            }
        } while (opcao != 0);
    }

    static void cadastrarCliente() {
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("Telefone: ");
        String telefone = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        clienteDAO.inserir(nome, telefone, email);
    }

    static void listarClientes() {
        System.out.println("\n=== LISTA DE CLIENTES ===");
        for (Cliente c : clienteDAO.listar()) {
            System.out.println(c);
        }
    }

    static void cadastrarVeiculo() {
        System.out.print("Placa: ");
        String placa = scanner.nextLine();
        System.out.print("Modelo: ");
        String modelo = scanner.nextLine();
        System.out.print("Cor: ");
        String cor = scanner.nextLine();
        System.out.print("ID do Cliente: ");
        int idCliente = scanner.nextInt();
        scanner.nextLine();
        veiculoDAO.inserir(placa, modelo, cor, idCliente);
    }

    static void listarVeiculos() {
        System.out.println("\n=== VEÍCULOS CADASTRADOS ===");
        for (Veiculo v : veiculoDAO.listar()) {
            System.out.println(v);
        }
    }

    static void listarVagas() {
        System.out.println("\n=== VAGAS DISPONÍVEIS ===");
        for (Vaga v : vagaDAO.listarDisponiveis()) {
            System.out.println(v);
        }
    }

    static void registrarEntrada() {
        listarVeiculos();
        System.out.print("ID do Veículo: ");
        int idVeiculo = scanner.nextInt();
        listarVagas();
        System.out.print("ID da Vaga: ");
        int idVaga = scanner.nextInt();
        scanner.nextLine();

        // Verifica se o veículo já está estacionado
        if (movimentacaoDAO.veiculoEstacionado(idVeiculo)) {
            System.out.println("❌ Este veículo já está estacionado em outra vaga!");
            return;
        }

        // Verifica se a vaga está disponível
        if (!vagaDAO.vagaDisponivel(idVaga)) {
            System.out.println("❌ Esta vaga já está ocupada!");
            return;
        }

        movimentacaoDAO.registrarEntrada(idVeiculo, idVaga);
        vagaDAO.ocuparVaga(idVaga, true);
        System.out.println("✅ Entrada registrada com sucesso!");
    }

    static void registrarSaida() {
        // Lista veículos estacionados
        System.out.println("\n=== VEÍCULOS ESTACIONADOS ===");
        List<Movimentacao> estacionados = movimentacaoDAO.listarEstacionados();

        if (estacionados.isEmpty()) {
            System.out.println("Não há veículos estacionados no momento.");
            return;
        }

        // Mostra informações básicas dos veículos estacionados
        for (Movimentacao m : estacionados) {
            Veiculo v = veiculoDAO.buscarPorId(m.getIdVeiculo());
            Vaga vaga = vagaDAO.buscarPorId(m.getIdVaga());
            System.out.printf("ID Veículo: %d | Placa: %s | Vaga: %d%n",
                    m.getIdVeiculo(), v.getPlaca(), vaga.getNumeroVaga());
        }

        System.out.print("\nID do Veículo para registrar saída: ");
        int idVeiculo = scanner.nextInt();
        scanner.nextLine();

        // Obtém a vaga associada automaticamente
        int idVaga = movimentacaoDAO.obterIdVagaPorVeiculo(idVeiculo);

        if (idVaga == -1) {
            System.out.println("❌ Veículo não encontrado ou já deixou o estacionamento!");
            return;
        }

        // Registra a saída
        movimentacaoDAO.registrarSaida(idVeiculo);

        // Libera a vaga automaticamente
        vagaDAO.liberarVaga(idVaga);
        System.out.println("✅ Saída registrada e vaga liberada com sucesso!");
    }

    static void listarMovimentacoes() {
        System.out.println("\n=== LISTA DE MOVIMENTAÇÕES ===");
        for (Movimentacao m : movimentacaoDAO.listar()) {
            System.out.println(m);
        }
    }
}