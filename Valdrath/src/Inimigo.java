// Classe que representa os inimigos que o jogador enfrenta nas batalhas
public class Inimigo {
    String nome;
    int vida;
    int dano;
    int ouro;
    boolean boss;

    public Inimigo(String nome, int vida, int dano, int ouro, boolean boss) {
        this.nome = nome;
        this.vida = vida;
        this.dano = dano;
        this.ouro = ouro;
        this.boss = boss;
    }

    public boolean estaVivo() {
        if (vida > 0) {
            return true;
        } else {
            return false;
        }
    }

    public void receberDano(int quantidade) {
        vida = vida - quantidade;
        if (vida < 0) {
            vida = 0;
        }
    }

    public void registro() {
        System.out.println("Inimigo: " + nome + " | Vida: " + vida + " | Dano: " + dano);
    }

    // inimigos normais que aparecem durante a historia
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

    // chefes (bosses), aparecem no final de cada rota da historia
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
