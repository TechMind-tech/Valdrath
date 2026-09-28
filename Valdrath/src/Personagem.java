// Classe do personagem principal (o heroi que o jogador controla)
public class Personagem {
    String nome;
    int forca;
    int inteligencia;
    int agilidade;
    int vida;
    int vidaMaxima;
    int ouro;
    int pocoes;
    Item espada;

    // metodo construtor, roda quando a gente cria um personagem novo
    public Personagem(String nome, int forca, int inteligencia, int agilidade, int vida) {
        this.nome = nome;
        this.forca = forca;
        this.inteligencia = inteligencia;
        this.agilidade = agilidade;
        this.vida = vida;
        this.vidaMaxima = vida;
        this.ouro = 80;
        this.pocoes = 2;
        this.espada = Item.espadaFerro();
    }

    // mostra os dados do personagem
    public void registro() {
        System.out.println("=== FICHA ===");
        System.out.println("Nome: " + nome);
        System.out.println("Vida: " + vida + "/" + vidaMaxima);
        System.out.println("Forca: " + forca);
        System.out.println("Inteligencia: " + inteligencia);
        System.out.println("Agilidade: " + agilidade);
        System.out.println("Ouro: " + ouro);
        System.out.println("Pocoes: " + pocoes);
        System.out.println("Arma: " + espada.nome);
        System.out.println("=============");
    }

    // retorna true se ainda tiver vida
    public boolean estaVivo() {
        if (vida > 0) {
            return true;
        } else {
            return false;
        }
    }

    // tira vida do personagem quando ele leva dano
    public void receberDano(int quantidade) {
        vida = vida - quantidade;
        if (vida < 0) {
            vida = 0;
        }
    }

    // aumenta a vida (usado quando bebe pocao)
    public void curar(int quantidade) {
        vida = vida + quantidade;
        if (vida > vidaMaxima) {
            vida = vidaMaxima;
        }
    }

    // usa uma pocao do inventario, se tiver
    public boolean usarPocao() {
        if (pocoes <= 0) {
            return false;
        }
        pocoes = pocoes - 1;
        curar(Item.pocaoCura().cura);
        return true;
    }

    // usado na escolha de doar tudo pra vila
    public void zerarOuro() {
        ouro = 0;
    }

    public void gastarPocoes() {
        pocoes = 0;
    }

    // usado na escolha de saquear a vila
    public void saquearVila() {
        ouro = ouro + 60;
        pocoes = pocoes + 2;
    }
}
