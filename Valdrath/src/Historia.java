import java.util.Scanner;
public class Historia {
    public static void Introducao(Scanner scanner) {
      
        System.out.println("╔══════════════════════════════════════════╗\r\n" + //
                        "║                                          ║\r\n" + //
                        "║   No princípio, houve a Chama.           ║\r\n" + //
                        "║                                          ║\r\n" + //
                        "╚══════════════════════════════════════════╝");
        SpriteRender.desenhar("sprites/voz_da_chama.png");
          scanner.nextLine();
        System.out.println(" .~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\r\n" + //
                        " :                                            :\r\n" + //
                        " :   Dizem que ela caiu dos céus há mil anos, :\r\n" + //
                        " :   e que mantém Valdrath viva.              :\r\n" + //
                        " :                                            :\r\n" + //
                        " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
                          scanner.nextLine();
        System.out.println(" .~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\r\n" + //
                        " :                                            :\r\n" + //
                        " :   Mas a luz começou a falhar.              :\r\n" + //
                        " :   Uma praga rasteja pela terra.            :\r\n" + //
                        " :   A Ordem tem um culpado: Malachar.        :\r\n" + //
                        " :                                            :\r\n" + //
                        " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
        scanner.nextLine();
        System.out.println(" .~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\r\n" + //
                        " :                                            :\r\n" + //
                        " :   Mas a luz começou a falhar.              :\r\n" + //
                        " :   Uma praga rasteja pela terra.            :\r\n" + //
                        " :   A Ordem tem um culpado: Malachar.        :\r\n" + //
                        " :                                            :\r\n" + //
                        " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
        System.out.println(" .~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\r\n" + //
                        " :                                            :\r\n" + //
                        " :   E tem uma solução: você.                 :\r\n" + //
                        " :                                            :\r\n" + //
                        " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
        scanner.nextLine();
        
    }

    public static void AldricFala (Scanner scanner) {

        System.out.println(
        " .~~[ ALDRIC ]~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
        " :                                            :\n" +
        " :   Levante a cabeça, recruta. Hoje você     :\n" +
        " :   deixa de ser aprendiz.                   :\n" +
        " :                                            :\n" +
        " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
        scanner.nextLine();

    }
}
