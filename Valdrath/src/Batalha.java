import java.util.Scanner;
public class Batalha {
    public static void iniciarBatalha(Personagem personagem, Inimigo inimigo) {
        System.out.println("Uma batalha começou entre " + personagem.nome + " e " + inimigo.nome + "!");
        System.out.println(personagem.nome + " tem " + personagem.Vida + " pontos de vida.");
        System.out.println(inimigo.nome + " tem " + inimigo.Vida + " pontos de vida.");
         }
        // Aqui você pode adicionar a lógica da batalha, como ataques, defesa, etc.


        public static  Integer atacar(Personagem personagem, Inimigo inimigo, Item item) {
        // Lógica de ataque do personagem ao inimigo
        int dano = personagem.força + item.dano; // Exemplo: dano baseado na força do personagem
        inimigo.Vida -= dano;
        System.out.println(personagem.nome + " atacou " + inimigo.nome + " causando " + dano + " de dano!");
        System.out.println(inimigo.nome + " agora tem " + inimigo.Vida + " pontos de vida.");
        return dano;
    }
    public static void OpcoesBatalha(Scanner scanner, Personagem personagem, Inimigo inimigo) {
        System.out.println("Escolha uma ação:");
        System.out.println("1. Atacar");
        System.out.println("2. Defender");
        System.out.println("3. Usar Item");
        System.out.print("Digite o número da ação desejada: ");
        int acao = scanner.nextInt();
        scanner.nextLine(); // Limpar o buffer do scanner
        
        switch (acao) {
            case 1:
                System.out.println("Você escolheu atacar!");
                atacar(personagem, inimigo, Item.espadaFerro()); // Supondo que o personagem tenha um item para atacar
                break;
            case 2:
                System.out.println("Você escolheu defender!");
                // Lógica de defesa
                break;
            case 3:
                System.out.println("Você escolheu usar um item!");
                Item.pocaoCura(); // Supondo que o personagem tenha uma poção decura
                break;
            default:
                System.out.println("Ação inválida! Tente novamente.");
                OpcoesBatalha(scanner, personagem, inimigo); // Chamar novamente para escolher uma ação válida
                break;
        }

        

        
    }
}
