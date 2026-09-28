import java.util.Scanner;

// Essa classe controla a batalha por turnos entre o heroi e um inimigo.
// Funciona assim: mostra a vida dos dois, o jogador escolhe uma acao
// (atacar, defender ou usar pocao), aplica o efeito, e depois o inimigo
// ataca de volta. Isso repete até um dos dois ficar com 0 de vida.
public class Batalha {

    // roda o combate. devolve true se o heroi ganhou, false se perdeu.
    // "ajudaAliado" e o dano extra que aliados causam por turno (em
    // batalha normal isso e 0, so os bosses tem ajuda)
    public static boolean combate(Scanner scanner, Personagem heroi, Inimigo inimigo, int ajudaAliado) {

        System.out.println();
        System.out.println(">>> BATALHA: " + heroi.nome + " contra " + inimigo.nome + " <<<");

        while (heroi.estaVivo() && inimigo.estaVivo()) {

            System.out.println();
            System.out.println("Sua vida:  " + heroi.vida + "/" + heroi.vidaMaxima);
            System.out.println(inimigo.nome + ": " + inimigo.vida + " de vida");
            System.out.println("Escolha uma acao:");
            System.out.println("  [1] Atacar");
            System.out.println("  [2] Defender (reduz o proximo dano)");
            System.out.println("  [3] Usar pocao (" + heroi.pocoes + " restantes)");
            System.out.print("  > ");

            String entrada = scanner.nextLine().trim();

            boolean defendendo = false;

            if (entrada.equals("1")) {
                int dano = heroi.forca + heroi.espada.dano;
                inimigo.receberDano(dano);
                System.out.println("Voce atacou e causou " + dano + " de dano!");

            } else if (entrada.equals("2")) {
                defendendo = true;
                System.out.println("Voce se prepara para defender o proximo golpe.");

            } else if (entrada.equals("3")) {
                boolean usou = heroi.usarPocao();
                if (usou) {
                    System.out.println("Voce bebeu uma pocao. Vida agora: " + heroi.vida);
                } else {
                    System.out.println("Voce nao tem pocoes! O turno foi perdido.");
                }

            } else {
                System.out.println("Acao invalida. Voce hesitou e perdeu o turno.");
            }

            // se tiver aliado ajudando, ele bate no inimigo todo turno
            if (ajudaAliado > 0 && inimigo.estaVivo()) {
                inimigo.receberDano(ajudaAliado);
                System.out.println("Seus aliados atacam e causam " + ajudaAliado + " de dano!");
            }

            // turno do inimigo (so ataca se ainda tiver vivo)
            if (inimigo.estaVivo()) {
                int danoInimigo = inimigo.dano;
                if (defendendo) {
                    danoInimigo = danoInimigo / 2;
                }
                heroi.receberDano(danoInimigo);
                System.out.println(inimigo.nome + " atacou e causou " + danoInimigo + " de dano.");
            }
        }

        System.out.println();
        if (heroi.estaVivo()) {
            System.out.println("*** Voce venceu " + inimigo.nome + "! ***");
            if (inimigo.ouro > 0) {
                heroi.ouro = heroi.ouro + inimigo.ouro;
                System.out.println("Voce recebeu " + inimigo.ouro + " de ouro.");
            }
            return true;
        } else {
            System.out.println("*** Voce foi derrotado por " + inimigo.nome + "... ***");
            return false;
        }
    }

    // batalha comum, sem ajuda de aliado
    public static boolean iniciar(Scanner scanner, Personagem heroi, Inimigo inimigo) {
        return combate(scanner, heroi, inimigo, 0);
    }

    // batalha de chefe. cada aliado que o jogador tem soma 6 de dano por turno
    public static boolean iniciarBoss(Scanner scanner, Personagem heroi, Inimigo boss, int quantidadeAliados) {
        System.out.println();
        System.out.println("############# BATALHA DE CHEFE #############");
        int ajuda = quantidadeAliados * 6;
        if (ajuda > 0) {
            System.out.println("Voce entra na luta acompanhado. Ajuda por turno: " + ajuda);
        } else {
            System.out.println("Voce enfrenta este chefe sozinho.");
        }
        return combate(scanner, heroi, boss, ajuda);
    }

    // se o heroi perder uma luta obrigatoria da historia, ele nao pode
    // morrer de vez (senao o jogo trava). aqui a gente da uma forcinha
    // pra historia continuar.
    public static void garantirSobrevivencia(Personagem heroi) {
        if (!heroi.estaVivo()) {
            System.out.println();
            System.out.println("(Voce quase morre, mas reune forcas para continuar a jornada.)");
            heroi.vida = 30;
        }
    }
}
