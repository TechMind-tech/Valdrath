
import java.util.Scanner;
public class Loja {

   public static void mostrarOpcoesLoja() {
        System.out.println("Bem-vindo à Loja!");
        System.out.println("1. Comprar Espada de Ferro Draco");
        System.out.println("2. Comprar Poção de Cura");
        System.out.println("3. Sair da Loja");
    }
    public static void abrirLoja(Personagem personagem) {

            int opcao = 0;
        while (opcao != 1 && opcao != 2 && opcao != 3) {
            mostrarOpcoesLoja();
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();
            scanner.nextLine(); // Limpar o buffer do scanner

            switch (opcao) {
                case 1:
                    System.out.println("Você comprou a Espada de Ferro Draco!");
                    personagem.espada = Item.espadaFerro().nome;
                    break;
                case 2:
                    System.out.println("Você comprou a Poção de Cura!");
                    // Lógica para adicionar a poção ao inventário do personagem
                    break;
                case 3:
                    System.out.println("Saindo da loja...");
                    break;
                default:
                    System.out.println("Opção inválida! Tente novamente.");
                    break;
            }
        }   
    

    }
    
}
