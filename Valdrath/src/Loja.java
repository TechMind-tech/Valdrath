import java.util.Scanner;

// Loja do Korrin, aparece no ato 2. O jogador pode comprar espada melhor
// ou pocao de cura usando o ouro que ele foi juntando.
public class Loja {

    static void mostrarOpcoesLoja(Personagem p) {
        Item espada = Item.espadaDraco();
        Item pocao = Item.pocaoCura();
        System.out.println();
        System.out.println("=== FORJA DE KORRIN ===");
        System.out.println("Seu ouro: " + p.ouro + " | Pocoes: " + p.pocoes);
        System.out.println("  [1] " + espada.nome + " (+" + espada.dano + " dano) - " + espada.preco + " ouro");
        System.out.println("  [2] " + pocao.nome + " (cura " + pocao.cura + ") - " + pocao.preco + " ouro");
        System.out.println("  [3] Sair da loja");
        System.out.print("  > ");
    }

    // fica em loop mostrando o menu ate o jogador escolher sair
    public static void abrirLoja(Scanner scanner, Personagem personagem) {
        boolean saiu = false;

        while (saiu == false) {
            mostrarOpcoesLoja(personagem);
            String entrada = scanner.nextLine().trim();

            if (entrada.equals("1")) {
                Item espada = Item.espadaDraco();
                if (personagem.ouro >= espada.preco) {
                    personagem.ouro = personagem.ouro - espada.preco;
                    personagem.espada = espada;
                    System.out.println("Voce comprou a " + espada.nome + "! Agora ela esta equipada.");
                } else {
                    System.out.println("Ouro insuficiente para a " + espada.nome + ".");
                }

            } else if (entrada.equals("2")) {
                Item pocao = Item.pocaoCura();
                if (personagem.ouro >= pocao.preco) {
                    personagem.ouro = personagem.ouro - pocao.preco;
                    personagem.pocoes = personagem.pocoes + 1;
                    System.out.println("Voce comprou uma " + pocao.nome + ". Pocoes: " + personagem.pocoes);
                } else {
                    System.out.println("Ouro insuficiente para a " + pocao.nome + ".");
                }

            } else if (entrada.equals("3")) {
                System.out.println("Voce fecha a bolsa e se despede de Korrin.");
                saiu = true;

            } else {
                System.out.println("Opcao invalida. Tente novamente.");
            }
        }
    }
}
