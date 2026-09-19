import java.util.Scanner;

public class App {

        public static void main(String[] args) throws Exception {
            Scanner scanner = new Scanner(System.in);   


            int opcao = 0;
            while (opcao != 1 && opcao != 2 && opcao != 3) {

                    System.out.println("_          ___ _                      _\n" + //
                                                "   /_\\   ___  / __(_)_ _  _____ _ ___  __| |___\n" + //
                                                "  / _ \\ (_-< | (__| | ' \\|_ / _` (_-< / _` / -_)\n" + //
                                                " /_/ \\_\\/__/  \\___|_|_||_/__\\__,_/__/ \\__,_\\___|\n" + //
                                                "\n" + //
                                                " ██╗   ██╗ █████╗ ██╗     ██████╗ ██████╗  █████╗ ████████╗██╗  ██╗\n" + //
                                                " ██║   ██║██╔══██╗██║     ██╔══██╗██╔══██╗██╔══██╗╚══██╔══╝██║  ██║\n" + //
                                                " ██║   ██║███████║██║     ██║  ██║██████╔╝███████║   ██║   ███████║\n" + //
                                                " ╚██╗ ██╔╝██╔══██║██║     ██║  ██║██╔══██╗██╔══██║   ██║   ██╔══██║\n" + //
                                                "  ╚████╔╝ ██║  ██║███████╗██████╔╝██║  ██║██║  ██║   ██║   ██║  ██║\n" + //
                                                "   ╚═══╝  ╚═╝  ╚═╝╚══════╝╚═════╝ ╚═╝  ╚═╝╚═╝  ╚═╝   ╚═╝   ╚═╝  ╚═╝");
                    System.out.println("                                            ");
                    System.out.println(" ~•~  a chama que consome tudo  ~•~");
                    System.out.println("                                            ");
                    System.out.println("1. Jogar");
                    System.out.println("2. Historia");
                    System.out.println("3. Sair");
                    System.out.print("Escolha uma opção:");
                if (opcao != 1 && opcao != 2 && opcao != 3 && opcao != 0) {
                    System.out.println("Digite 1 para opção A, 2 para opção B ou 3 para opção C: ");
                }
                opcao = scanner.nextInt();
                scanner.nextLine();
                
            } 

            Historia.AldricFala(scanner);


            Personagem.registro(Personagem.Heroi);
            Historia.Introducao(scanner);
            
            
            Personagem.registro(Personagem.Heroi);
            Inimigo.loboSombrio().registro();
            Batalha.iniciarBatalha(Personagem.Heroi, Inimigo.loboSombrio());
            Inimigo lobo = Inimigo.loboSombrio();
            Batalha.OpcoesBatalha(scanner, Personagem.Heroi, lobo);
            Batalha.OpcoesBatalha(scanner,Personagem.Heroi, lobo);
            
            scanner.close();
        }
    
}
