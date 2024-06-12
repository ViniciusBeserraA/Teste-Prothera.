import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        Funcionario[] funcionarios = new Funcionario[10];
        DecimalFormat df = new DecimalFormat("#,##0.00");
        final double SALARIO_MINIMO = 1212.00;

        funcionarios[0] = new Funcionario("Maria", "18/10/2000", 2009.44, "Operador");
        funcionarios[1] = new Funcionario("João", "12/05/1990", 2284.38, "Operador");
        funcionarios[2] = new Funcionario("Caio", "02/05/1961", 9836.14, "Coordenador");
        funcionarios[3] = new Funcionario("Miguel", "14/10/1988", 19119.88, "Diretor");
        funcionarios[4] = new Funcionario("Alice", "05/01/1995", 2284.38, "Recepcionista");
        funcionarios[5] = new Funcionario("Heitor", "19/11/1999", 1582.72, "Operador");
        funcionarios[6] = new Funcionario("Arthur", "31/03/1993", 4071.84, "Contador");
        funcionarios[7] = new Funcionario("Laura", "08/07/1994", 3017.45, "Gerente");
        funcionarios[8] = new Funcionario("Heloisa", "24/05/2003", 1606.85, "Eletricista");
        funcionarios[9] = new Funcionario("Helena", "02/09/1996", 2799.93, "Gerente");


        List<Funcionario> listaFuncionarios = Arrays.asList(funcionarios);

        listaFuncionarios = listaFuncionarios.stream()
                .filter(funcionario -> !funcionario.getNome().equals("João"))
                .toList();

        System.out.println("------------- Lista de Funcionários ------------");
        for (Funcionario funcionario : listaFuncionarios) {
            System.out.println("Nome: " + funcionario.getNome());
            System.out.println("Data de Nascimento: " + funcionario.getDataNascimento());
            System.out.println("Salário: R$ " + df.format(funcionario.getSalario()));
            System.out.println("Função: " + funcionario.getFuncao());
            System.out.println();
        }
        System.out.println("----------------------------------------------");
        System.out.println();

        listaFuncionarios = listaFuncionarios.stream()
                .peek(funcionario -> funcionario.setSalario(funcionario.getSalario() * 1.1))
                .toList();

        System.out.println("------------- Lista de Funcionários: Atualizada Pós-aumento salarial 10% ------------");
        for (Funcionario funcionario : listaFuncionarios) {
            System.out.println("Nome: " + funcionario.getNome());
            System.out.println("Data de Nascimento: " + funcionario.getDataNascimento());
            System.out.println("Salário: R$ " + df.format(funcionario.getSalario()));
            System.out.println("Função: " + funcionario.getFuncao());
            System.out.println();

        }
        System.out.println("----------------------------------------------");
        System.out.println();

        Map<String, List<Funcionario>> funcionariosByFuncao = listaFuncionarios.stream()
                .collect(Collectors.groupingBy(Funcionario::getFuncao));

        System.out.println("------------- Lista de Funcionários agrupado por função e valor ------------");
        for (Map.Entry<String, List<Funcionario>> entry : funcionariosByFuncao.entrySet()) {
            System.out.println("Função: " + entry.getKey());
            System.out.println("----------------------");
            for (Funcionario funcionario : entry.getValue()) {
                System.out.println("Nome: " + funcionario.getNome());
                System.out.println("Data de Nascimento: " + funcionario.getDataNascimento());
                System.out.println("Salário: R$ " + df.format(funcionario.getSalario()));
                System.out.println();
            }
        }
        System.out.println("----------------------------------------------");
        System.out.println();

        Map<String, List<Funcionario>> funcionariosAniversario = listaFuncionarios.stream()
                .filter(funcionario -> {
                    int mesAniversario = Integer.parseInt(funcionario.getDataNascimento().split("/")[1]);
                    return mesAniversario == 10 || mesAniversario == 12;
                })
                .collect(Collectors.groupingBy(funcionario -> {
                    int mesAniversario = Integer.parseInt(funcionario.getDataNascimento().split("/")[1]);
                    return mesAniversario == 10 ? "Outubro" : "Dezembro";
                }));

        System.out.println("------------- Lista de Funcionários agrupado por aniversariantes do mês 10 e 12 ------------");
        for (Map.Entry<String, List<Funcionario>> entry : funcionariosAniversario.entrySet()) {
            System.out.println("Aniversariantes do mês : " + entry.getKey());
            System.out.println("----------------------");
            for (Funcionario funcionario : entry.getValue()) {
                System.out.println("Nome: " + funcionario.getNome());
                System.out.println("Data de Nascimento: " + funcionario.getDataNascimento());
                System.out.println("Salário: R$ " + df.format(funcionario.getSalario()));
                System.out.println();
            }
        }
        System.out.println("---------------------------------------------------------------------------------------------");
        System.out.println();

        Optional<Funcionario> funcionarioMaiorIdade = listaFuncionarios.stream()
                .min((funcionario1, funcionario2) -> {
                    Date dataNascimento1 = convertData(funcionario1.getDataNascimento());
                    Date dataNascimento2 = convertData(funcionario2.getDataNascimento());
                    assert dataNascimento1 != null;
                    return dataNascimento1.compareTo(dataNascimento2);
                });


        System.out.println("------------- Funcionário mais velho ------------");
            if (funcionarioMaiorIdade.isPresent()) {
                Funcionario funcionario = funcionarioMaiorIdade.get();
                System.out.println("Nome: " + funcionario.getNome());
                System.out.println("Data de Nascimento: " + funcionario.getDataNascimento());
                System.out.println();
            }

        System.out.println("------------------------------------------------------");
        System.out.println();


        System.out.println("------------- Lista de Funcionários: Ordem Alfabética A-Z ------------");
        listaFuncionarios.stream()
                .sorted(Comparator.comparing(Funcionario::getNome))
                .forEach(f -> {
                    System.out.println("Nome: " + f.getNome());
                    System.out.println("Data de Nascimento: " + f.getDataNascimento());
                    System.out.println("Salário: R$ " + df.format(f.getSalario()));
                    System.out.println("Função: " + f.getFuncao());
                    System.out.println();
                });


        System.out.println("----------------------------------------------");
        System.out.println();

        double sumSalariosFuncionarios = listaFuncionarios.stream()
                .mapToDouble(Funcionario::getSalario)
                .sum();

        System.out.println("------------- Valor total da folha de funcionarios (Somatório dos Salarios) ------------");
        System.out.println();
        System.out.println("Valor total: R$ " + df.format(sumSalariosFuncionarios));
        System.out.println();
        System.out.println("----------------------------------------------");
        System.out.println();


        Map<String, Double> salariosMinimosPorFuncionario = new HashMap<>();

        listaFuncionarios.forEach(funcionario -> {
            double salariosMinimos = funcionario.getSalario() / SALARIO_MINIMO;
            salariosMinimosPorFuncionario.put(funcionario.getNome(), salariosMinimos);
        });

        System.out.println("------------- Lista de Funcionários: Quantidade salários mínimos por funcionários ------------");
        salariosMinimosPorFuncionario.forEach((nome, qtdSalariosMinimos) -> {
            System.out.println("Nome: " + nome);
            System.out.println("Quantidade de salários mínimos: " + df.format(qtdSalariosMinimos));
            System.out.println();
        });
        System.out.println("----------------------------------------------");
        System.out.println();
    }

    private static Date convertData(String dataString) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            return formatter.parse(dataString);
        } catch (ParseException e) {
            return null;
        }
    }
}
