import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

public class Principal {
    private static final DateTimeFormatter FORMATADOR_DATA = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final DecimalFormat FORMATADOR_VALOR;
    private static final BigDecimal SALARIO_MINIMO = new BigDecimal("1212.00");
    private static final BigDecimal PERCENTUAL_AUMENTO = new BigDecimal("1.10");

    static {
        DecimalFormatSymbols simbolos = new DecimalFormatSymbols(new Locale("pt", "BR"));
        simbolos.setDecimalSeparator(',');
        simbolos.setGroupingSeparator('.');
        FORMATADOR_VALOR = new DecimalFormat("#,##0.00", simbolos);
    }

    public static void main(String[] args) {
        List<Funcionario> funcionarios = new ArrayList<>(criarFuncionarios());

        System.out.println("3.1 - Funcionarios inseridos:");
        imprimirFuncionarios(funcionarios);

        // Remove o João da lista
        funcionarios.removeIf(funcionario -> funcionario.getNome().equalsIgnoreCase("João"));
        System.out.println("\n3.2 - Lista apos remover João:");
        imprimirFuncionarios(funcionarios);

        aplicarAumento(funcionarios);
        System.out.println("\n3.4 - Lista apos aumento de 10%:");
        imprimirFuncionarios(funcionarios);

        Map<String, List<Funcionario>> funcionariosPorFuncao = agruparPorFuncao(funcionarios);
        System.out.println("\n3.5 e 3.6 - Funcionarios agrupados por funcao:");
        imprimirAgrupadosPorFuncao(funcionariosPorFuncao);

        System.out.println("\n3.8 - Funcionarios que fazem aniversario nos meses 10 e 12:");
        imprimirAniversariantes(funcionarios, 10, 12);

        System.out.println("\n3.9 - Funcionario com maior idade:");
        imprimirFuncionarioMaisVelho(funcionarios);

        System.out.println("\n3.10 - Funcionarios em ordem alfabetica:");
        imprimirFuncionariosOrdenados(funcionarios);

        System.out.println("\n3.11 - Total dos salarios:");
        BigDecimal totalSalarios = somarSalarios(funcionarios);
        System.out.println("Total: R$ " + formatarValor(totalSalarios));

        System.out.println("\n3.12 - Quantidade de salarios minimos por funcionario:");
        imprimirSalariosMinimos(funcionarios);
    }

    private static List<Funcionario> criarFuncionarios() {
        List<Funcionario> funcionarios = new ArrayList<>();

        funcionarios.add(new Funcionario("Maria", LocalDate.of(2000, 10, 18), new BigDecimal("2009.44"), "Operador"));
        funcionarios.add(new Funcionario("João", LocalDate.of(1990, 5, 12), new BigDecimal("2284.38"), "Operador"));
        funcionarios.add(new Funcionario("Caio", LocalDate.of(1961, 5, 2), new BigDecimal("9836.14"), "Coordenador"));
        funcionarios.add(new Funcionario("Miguel", LocalDate.of(1988, 10, 14), new BigDecimal("19119.88"), "Diretor"));
        funcionarios.add(new Funcionario("Alice", LocalDate.of(1995, 1, 5), new BigDecimal("2234.68"), "Recepcionista"));
        funcionarios.add(new Funcionario("Heitor", LocalDate.of(1999, 11, 9), new BigDecimal("1582.72"), "Operador"));
        funcionarios.add(new Funcionario("Arthur", LocalDate.of(1993, 3, 31), new BigDecimal("4071.84"), "Contador"));
        funcionarios.add(new Funcionario("Laura", LocalDate.of(1994, 7, 8), new BigDecimal("3017.45"), "Gerente"));
        funcionarios.add(new Funcionario("Heloísa", LocalDate.of(2003, 5, 24), new BigDecimal("1606.85"), "Eletricista"));
        funcionarios.add(new Funcionario("Helena", LocalDate.of(1996, 9, 2), new BigDecimal("2799.93"), "Gerente"));

        return funcionarios;
    }

    private static void aplicarAumento(List<Funcionario> funcionarios) {
        for (Funcionario funcionario : funcionarios) {
            // O aumento foi aplicado multiplicando R$1.10
            BigDecimal salarioAtualizado = funcionario.getSalario()
                    .multiply(PERCENTUAL_AUMENTO)
                    .setScale(2, RoundingMode.HALF_UP);
            funcionario.setSalario(salarioAtualizado);
        }
    }

    private static Map<String, List<Funcionario>> agruparPorFuncao(List<Funcionario> funcionarios) {
        // rever a ordem das funcoes conforme elas aparecem na lista
        return funcionarios.stream()
                .collect(Collectors.groupingBy(Funcionario::getFuncao, LinkedHashMap::new, Collectors.toList()));
    }

    private static void imprimirFuncionarios(List<Funcionario> funcionarios) {
        for (Funcionario funcionario : funcionarios) {
            System.out.println(formatarFuncionario(funcionario));
        }
    }

    private static void imprimirAgrupadosPorFuncao(Map<String, List<Funcionario>> funcionariosPorFuncao) {
        for (Map.Entry<String, List<Funcionario>> entry : funcionariosPorFuncao.entrySet()) {
            System.out.println("Funcao: " + entry.getKey());
            imprimirFuncionarios(entry.getValue());
            System.out.println();
        }
    }

    private static void imprimirAniversariantes(List<Funcionario> funcionarios, int... meses) {
        for (Funcionario funcionario : funcionarios) {
            int mesNascimento = funcionario.getDataNascimento().getMonthValue();
            for (int mes : meses) {
                if (mesNascimento == mes) {
                    System.out.println(formatarFuncionario(funcionario));
                    break;
                }
            }
        }
    }

    private static void imprimirFuncionarioMaisVelho(List<Funcionario> funcionarios) {

        Funcionario funcionarioMaisVelho = funcionarios.stream()
                .min(Comparator.comparing(Funcionario::getDataNascimento))
                .orElseThrow(() -> new IllegalStateException("Nenhum funcionario cadastrado."));

        int idade = Period.between(funcionarioMaisVelho.getDataNascimento(), LocalDate.now()).getYears();
        System.out.println("Nome: " + funcionarioMaisVelho.getNome() + " | Idade: " + idade);
    }

    private static void imprimirFuncionariosOrdenados(List<Funcionario> funcionarios) {
        funcionarios.stream()
                .sorted(Comparator.comparing(Funcionario::getNome))
                .forEach(funcionario -> System.out.println(formatarFuncionario(funcionario)));
    }

    private static BigDecimal somarSalarios(List<Funcionario> funcionarios) {
        return funcionarios.stream()
                .map(Funcionario::getSalario)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private static void imprimirSalariosMinimos(List<Funcionario> funcionarios) {
        for (Funcionario funcionario : funcionarios) {
            BigDecimal quantidadeSalarios = funcionario.getSalario()
                    .divide(SALARIO_MINIMO, 2, RoundingMode.HALF_UP);

            System.out.println(
                    funcionario.getNome() + ": " + formatarValor(quantidadeSalarios) + " salarios minimos"
            );
        }
    }

    private static String formatarFuncionario(Funcionario funcionario) {
        return "Nome: " + funcionario.getNome()
                + " | Data Nascimento: " + funcionario.getDataNascimento().format(FORMATADOR_DATA)
                + " | Salario: R$ " + formatarValor(funcionario.getSalario())
                + " | Funcao: " + funcionario.getFuncao();
    }

    private static String formatarValor(BigDecimal valor) {
        return FORMATADOR_VALOR.format(valor);
    }
}
