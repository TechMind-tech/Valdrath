/*
 * Inimigo
 * -------
 * Representa qualquer criatura que luta contra o heroi: um lobo, um
 * esqueleto, ou ate um chefe (boss).
 *
 * Assim como Item, usamos metodos "fabrica" (static) para criar cada
 * inimigo do roteiro ja com os atributos certos. Fica facil de ler e
 * de ajustar a dificuldade.
 */
public class Inimigo {
    String nome;     // nome exibido na batalha
    int vida;        // vida atual
    int dano;        // quanto de dano ele causa ao atacar
    int ouro;        // ouro que o heroi ganha ao vence-lo
    boolean boss;    // verdadeiro se for um chefe (batalha mais dura)

    // Construtor completo.
    public Inimigo(String nome, int vida, int dano, int ouro, boolean boss) {
        this.nome = nome;
        this.vida = vida;
        this.dano = dano;
        this.ouro = ouro;
        this.boss = boss;
    }

    // Verdadeiro enquanto o inimigo tiver vida.
    public boolean estaVivo() {
        return vida > 0;
    }

    // Tira vida do inimigo.
    public void receberDano(int quantidade) {
        vida -= quantidade;
        if (vida < 0) {
            vida = 0;
        }
    }

    // Mostra os dados do inimigo (opcional, para depurar/testar).
    public void registro() {
        System.out.println("Inimigo: " + nome + " | Vida: " + vida + " | Dano: " + dano);
    }

    // ----------------- INIMIGOS COMUNS -----------------

    public static Inimigo loboSombrio() {
        return new Inimigo("Lobo Sombrio", 40, 6, 15, false);
    }

    public static Inimigo bandidoDesertor() {
        return new Inimigo("Bandido Desertor", 50, 8, 20, false);
    }

    public static Inimigo aldeaoCorrompido() {
        return new Inimigo("Aldeao Corrompido", 55, 9, 0, false);
    }

    public static Inimigo esqueletoGuardiao() {
        return new Inimigo("Esqueleto Guardiao", 65, 11, 30, false);
    }

    public static Inimigo espectroDosSacrificados() {
        return new Inimigo("Espectro dos Sacrificados", 70, 12, 0, false);
    }

    public static Inimigo cavaleiroDaOrdem() {
        return new Inimigo("Cavaleiro da Ordem", 80, 13, 40, false);
    }

    // ----------------- CHEFES (BOSSES) -----------------

    public static Inimigo aldricVerdadeiro() {
        return new Inimigo("Aldric Verdadeiro", 140, 16, 0, true);
    }

    public static Inimigo malacharBoss() {
        return new Inimigo("Malachar", 120, 15, 0, true);
    }

    public static Inimigo guardiaoDaChama() {
        return new Inimigo("Guardiao da Chama", 150, 17, 0, true);
    }
}
