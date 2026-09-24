import java.util.Scanner;

/*
 * Batalha
 * -------
 * Controla o combate por turnos entre o heroi e um inimigo.
 *
 * Como funciona (bem passo a passo, estilo estudante):
 *   1. Mostramos a vida dos dois.
 *   2. O jogador escolhe uma acao: Atacar, Defender ou Usar pocao.
 *   3. Aplicamos o efeito da acao no inimigo.
 *   4. Se o inimigo ainda estiver vivo, ele revida (ataca o heroi).
 *   5. Repetimos ate alguem chegar a zero de vida.
 *
 * "Defender" faz o heroi tomar menos dano no ataque do inimigo naquele turno.
 * O boss usa a mesma logica, so que com mais vida/dano e uma ajuda de aliados.
 */
public class Batalha {

    /*
     * combate(...)
     * Roda o loop de turnos ate alguem morrer.
     * Retorna true se o HEROI venceu, false se o heroi foi derrotado.
     * O parametro "ajudaAliado" e o dano extra que aliados (ex: Lyra, Malachar)
     * causam por turno. Em batalhas comuns ele vale 0.
     */
    public static boolean combate(Scanner scanner, Personagem heroi, Inimigo inimigo, int ajudaAliado) {

        System.out.println();
        System.out.println(">>> BATALHA: " + heroi.nome + " contra " + inimigo.nome + " <<<");

        // O loop continua enquanto os DOIS estiverem vivos.
        while (heroi.estaVivo() && inimigo.estaVivo()) {

            // Mostra o placar de vida no comeco de cada turno.
            System.out.println();
            System.out.println("Sua vida:  " + heroi.vida + "/" + heroi.vidaMaxima);
            System.out.println(inimigo.nome + ": " + inimigo.vida + " de vida");
            System.out.println("Escolha uma acao:");
            System.out.println("  [1] Atacar");
            System.out.println("  [2] Defender (reduz o proximo dano)");
            System.out.println("  [3] Usar pocao (" + heroi.pocoes + " restantes)");
            System.out.print("  > ");

            String entrada = scanner.nextLine().trim();

            // "defendendo" fica true quando o jogador escolhe defender neste turno.
            boolean defendendo = false;

            if (entrada.equals("1")) {
                // Dano do heroi = forca + dano da espada equipada.
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
                // Entrada invalida: avisamos e o heroi perde o turno de ataque,
                // mas o loop continua (nao trava o jogo).
                System.out.println("Acao invalida. Voce hesitou e perdeu o turno.");
            }

            // Se houver aliados ajudando, eles batem no inimigo todo turno.
            if (ajudaAliado > 0 && inimigo.estaVivo()) {
                inimigo.receberDano(ajudaAliado);
                System.out.println("Seus aliados atacam e causam " + ajudaAliado + " de dano!");
            }

            // Turno do inimigo: so ataca se ainda estiver vivo.
            if (inimigo.estaVivo()) {
                int danoInimigo = inimigo.dano;
                if (defendendo) {
                    // Defender corta o dano pela metade (divisao inteira).
                    danoInimigo = danoInimigo / 2;
                }
                heroi.receberDano(danoInimigo);
                System.out.println(inimigo.nome + " atacou e causou " + danoInimigo + " de dano.");
            }
        }

        // Saiu do loop: alguem morreu. Descobrimos quem.
        System.out.println();
        if (heroi.estaVivo()) {
            System.out.println("*** Voce venceu " + inimigo.nome + "! ***");
            if (inimigo.ouro > 0) {
                heroi.ouro += inimigo.ouro;
                System.out.println("Voce recebeu " + inimigo.ouro + " de ouro.");
            }
            return true;
        } else {
            System.out.println("*** Voce foi derrotado por " + inimigo.nome + "... ***");
            return false;
        }
    }

    /*
     * iniciar(...)
     * Atalho para uma batalha comum (sem ajuda de aliado).
     */
    public static boolean iniciar(Scanner scanner, Personagem heroi, Inimigo inimigo) {
        return combate(scanner, heroi, inimigo, 0);
    }

    /*
     * iniciarBoss(...)
     * Batalha de chefe. Recebe "quantidadeAliados": quanto mais aliados
     * (ex: Lyra + Malachar), maior a ajuda por turno. Cada aliado soma 6 de dano.
     */
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

    /*
     * garantirSobrevivencia(...)
     * Rede de seguranca para a HISTORIA nao terminar cedo demais.
     * Se o heroi perde uma batalha obrigatoria, ele "cai mas se levanta"
     * com um pouco de vida, para o roteiro continuar ate o final.
     */
    public static void garantirSobrevivencia(Personagem heroi) {
        if (!heroi.estaVivo()) {
            System.out.println();
            System.out.println("(Voce quase morre, mas reune forcas para continuar a jornada.)");
            heroi.vida = 30;
        }
    }
}
