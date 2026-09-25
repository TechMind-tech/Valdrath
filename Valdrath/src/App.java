import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/*
 * App
 * ---
 * Ponto de entrada do jogo "As Cinzas de Valdrath".
 * Mostra o menu principal e, ao escolher Jogar, entrega o controle para
 * Historia.jogar(scanner), que roda o roteiro inteiro (prologo -> atos ->
 * final decidido pelo karma).
 */
public class App {

    public static void main(String[] args) {
        // Garante UTF-8 na saida para as caixas de dialogo nao desalinharem.
        System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));

        Scanner scanner = new Scanner(System.in);

        boolean sair = false;
        while (!sair) {
            mostrarMenu();
            String entrada = scanner.nextLine().trim();

            switch (entrada) {
                case "1":
                    Historia.jogar(scanner);
                    System.out.println();
                    System.out.println("=== FIM DA JORNADA. Obrigado por jogar! ===");
                    System.out.println();
                    break;
                case "2":
                    mostrarSobre(scanner);
                    break;
                case "3":
                    sair = true;
                    System.out.println("Ate a proxima, cavaleiro.");
                    break;
                default:
                    System.out.println("Opcao invalida. Digite 1, 2 ou 3.");
                    break;
            }
        }

        scanner.close();
    }

    static void mostrarMenu() {
        System.out.println("    _          ___ _                      _");
        System.out.println("   /_\\   ___  / __(_)_ _  _____ _ ___  __| |___");
        System.out.println("  / _ \\ (_-< | (__| | ' \\|_ / _` (_-< / _` / -_)");
        System.out.println(" /_/ \\_\\/__/  \\___|_|_||_/__\\__,_/__/ \\__,_\\___|");
        System.out.println();
        System.out.println(" VALDRATH");
        System.out.println(" ~*~  a chama que consome tudo  ~*~");
        System.out.println();
        System.out.println("1. Jogar");
        System.out.println("2. Sobre a Historia");
        System.out.println("3. Sair");
        System.out.print("Escolha uma opcao: ");
    }

    static void mostrarSobre(Scanner scanner) {
        System.out.println();
        System.out.println("As Cinzas de Valdrath e uma aventura de escolhas.");
        System.out.println("Cada decisao soma HONRA ou AMBICAO e decide um de tres finais:");
        System.out.println("  - Heroi (A Aurora Renasce)");
        System.out.println("  - Vilao (O Novo Tirano)");
        System.out.println("  - Neutro (O Andarilho)");
        System.out.println();
        System.out.print("Pressione ENTER para voltar ao menu...");
        scanner.nextLine();
        System.out.println();
    }
}
