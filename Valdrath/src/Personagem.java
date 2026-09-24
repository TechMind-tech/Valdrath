/*
 * Personagem
 * ----------
 * Representa o heroi controlado pelo jogador.
 * Guarda os atributos (vida, forca, etc.), o ouro, a espada equipada
 * e quantas pocoes ele tem no inventario.
 *
 * Os metodos ajudam o resto do jogo a mexer nesses valores de um jeito
 * seguro (ex: nao deixar a vida passar do maximo).
 */
public class Personagem {
    String nome;          // nome escolhido pelo jogador
    int forca;            // usada para calcular o dano dos ataques
    int inteligencia;     // atributo de RPG (guardado para exibir)
    int agilidade;        // atributo de RPG (guardado para exibir)
    int vida;             // vida atual
    int vidaMaxima;       // vida maxima (a vida nunca passa disso)
    int ouro;             // dinheiro para gastar na loja
    int pocoes;           // quantas pocoes de cura estao no inventario
    Item espada;          // arma equipada no momento

    // Construtor: monta o heroi com os valores iniciais.
    public Personagem(String nome, int forca, int inteligencia, int agilidade, int vida) {
        this.nome = nome;
        this.forca = forca;
        this.inteligencia = inteligencia;
        this.agilidade = agilidade;
        this.vida = vida;
        this.vidaMaxima = vida;
        this.ouro = 80;                 // comeca com um pouco de ouro
        this.pocoes = 2;                // e duas pocoes de cura
        this.espada = Item.espadaFerro();
    }

    // Mostra a ficha do personagem na tela.
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

    // Verdadeiro enquanto o heroi tiver vida.
    public boolean estaVivo() {
        return vida > 0;
    }

    // Tira vida do heroi (sem deixar ficar negativa, so por organizacao).
    public void receberDano(int quantidade) {
        vida -= quantidade;
        if (vida < 0) {
            vida = 0;
        }
    }

    // Recupera vida (sem passar da vida maxima).
    public void curar(int quantidade) {
        vida += quantidade;
        if (vida > vidaMaxima) {
            vida = vidaMaxima;
        }
    }

    // Usa uma pocao: cura e diminui o estoque. Retorna false se nao houver pocao.
    public boolean usarPocao() {
        if (pocoes <= 0) {
            return false;
        }
        pocoes--;
        curar(Item.pocaoCura().cura);
        return true;
    }

    // --- metodos usados pelas escolhas do roteiro ---

    // Doar tudo para Brenna (Escolha 2, opcao 1).
    public void zerarOuro() {
        ouro = 0;
    }

    // Gastar/entregar todas as pocoes (parte da doacao).
    public void gastarPocoes() {
        pocoes = 0;
    }

    // Saquear a vila (Escolha 2, opcao 3): ganha recursos.
    public void saquearVila() {
        ouro += 60;
        pocoes += 2;
    }
}
