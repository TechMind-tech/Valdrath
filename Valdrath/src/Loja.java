import java.util.Scanner;

/*
 * Loja
 * ----
 * A loja de Korrin, no fim do Ato 2. O heroi pode gastar o ouro que juntou
 * comprando uma espada melhor ou pocoes de cura.
 *
 * Tudo mexe no inventario REAL do Personagem (ouro, pocoes, espada), entao
 * as compras aqui repercutem nas batalhas seguintes.
 */
public class Loja {

    // Mostra o cardapio com os precos vindos das "fabricas" de Item.
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

    // Abre a loja e fica em loop ate o jogador escolher sair.
    public static void abrirLoja(Scanner scanner, Personagem personagem) {
        boolean saiu = false;
        while (!saiu) {
            mostrarOpcoesLoja(personagem);
            String entrada = scanner.nextLine().trim();

            switch (entrada) {
                case "1": {
                    Item espada = Item.espadaDraco();
                    if (personagem.ouro >= espada.preco) {
                        personagem.ouro -= espada.preco;
                        personagem.espada = espada;
                        System.out.println("Voce comprou a " + espada.nome + "! Agora ela esta equipada.");
                    } else {
                        System.out.println("Ouro insuficiente para a " + espada.nome + ".");
                    }
                    break;
                }
                case "2": {
                    Item pocao = Item.pocaoCura();
                    if (personagem.ouro >= pocao.preco) {
                        personagem.ouro -= pocao.preco;
                        personagem.pocoes++;
                        System.out.println("Voce comprou uma " + pocao.nome + ". Pocoes: " + personagem.pocoes);
                    } else {
                        System.out.println("Ouro insuficiente para a " + pocao.nome + ".");
                    }
                    break;
                }
                case "3":
                    System.out.println("Voce fecha a bolsa e se despede de Korrin.");
                    saiu = true;
                    break;
                default:
                    System.out.println("Opcao invalida. Tente novamente.");
                    break;
            }
        }
    }
}
