import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Estacionamento {
    private int capacidade;
    private List<Veiculo> veiculosEstacionados;

    public Estacionamento(int capacidade) {
        this.capacidade = capacidade;
        this.veiculosEstacionados = new ArrayList<>();
    }

    public boolean adicionarVeiculo(Veiculo veiculo) {
        if (veiculosEstacionados.size() < capacidade) {
            veiculosEstacionados.add(veiculo);
            return true;
        }
        return false;
    }

    public boolean removerVeiculo(String placa) {
        for (Veiculo veiculo : veiculosEstacionados) {
            if (veiculo.getPlaca().equals(placa)) {
                veiculosEstacionados.remove(veiculo);
                return true;
            }
        }
        return false;
    }

    public int vagasDisponiveis() {
        return capacidade - veiculosEstacionados.size();
    }
}

class Veiculo {
    private String placa;
    private LocalDateTime horaEntrada;
    private LocalDateTime horaSaida;

    public Veiculo(String placa) {
        this.placa = placa;
    }

    public String getPlaca() {
        return placa;
    }

    public void registrarEntrada(LocalDateTime horaEntrada) {
        this.horaEntrada = horaEntrada;
    }

    public void registrarSaida(LocalDateTime horaSaida) {
        this.horaSaida = horaSaida;
    }

    public long calcularTempoEstacionamento() {
        if (horaEntrada != null && horaSaida != null) {
            return Duration.between(horaEntrada, horaSaida).toHours();
        }
        return 0;
    }
}

class Carro extends Veiculo {
    public static final double TARIFA_POR_HORA = 5.00;

    public Carro(String placa) {
        super(placa);
    }

    public double calcularValorPago() {
        long horasEstacionadas = calcularTempoEstacionamento();
        return horasEstacionadas * TARIFA_POR_HORA;
    }
}

class Moto extends Veiculo {
    public static final double TARIFA_POR_HORA = 2.50;

    public Moto(String placa) {
        super(placa);
    }

    public double calcularValorPago() {
        long horasEstacionadas = calcularTempoEstacionamento();
        return horasEstacionadas * TARIFA_POR_HORA;
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        Estacionamento estacionamento = new Estacionamento(10);

        System.out.println("Digite a placa do carro:");
        String placaCarro = scanner.nextLine();
        Carro carro = new Carro(placaCarro);

        System.out.println("Digite a hora de entrada do carro (formato: yyyy-MM-dd HH:mm):");
        String entradaCarroStr = scanner.nextLine();
        LocalDateTime entradaCarro = LocalDateTime.parse(entradaCarroStr, formatter);
        carro.registrarEntrada(entradaCarro);

        System.out.println("Digite a hora de saída do carro (formato: yyyy-MM-dd HH:mm):");
        String saidaCarroStr = scanner.nextLine();
        LocalDateTime saidaCarro = LocalDateTime.parse(saidaCarroStr, formatter);
        carro.registrarSaida(saidaCarro);

        estacionamento.adicionarVeiculo(carro);

        System.out.println("Digite a placa da moto:");
        String placaMoto = scanner.nextLine();
        Moto moto = new Moto(placaMoto);

        System.out.println("Digite a hora de entrada da moto (formato: yyyy-MM-dd HH:mm):");
        String entradaMotoStr = scanner.nextLine();
        LocalDateTime entradaMoto = LocalDateTime.parse(entradaMotoStr, formatter);
        moto.registrarEntrada(entradaMoto);

        System.out.println("Digite a hora de saída da moto (formato: yyyy-MM-dd HH:mm):");
        String saidaMotoStr = scanner.nextLine();
        LocalDateTime saidaMoto = LocalDateTime.parse(saidaMotoStr, formatter);
        moto.registrarSaida(saidaMoto);

        estacionamento.adicionarVeiculo(moto);

        System.out.println("Vagas disponiveis: " + estacionamento.vagasDisponiveis());

        System.out.println("Valor a pagar pelo carro: " + carro.calcularValorPago());
        System.out.println("Valor a pagar pela moto: " + moto.calcularValorPago());

        estacionamento.removerVeiculo(carro.getPlaca());
        estacionamento.removerVeiculo(moto.getPlaca());

        System.out.println("Vagas disponiveis: " + estacionamento.vagasDisponiveis());
    }
}
