// Classe que representa um item do jogo (espada ou pocao)
public class Item {
    String nome;
    String descricao;
    int dano;
    int cura;
    int preco;

    public Item(String nome, String descricao, int dano, int cura, int preco) {
        this.nome = nome;
        this.descricao = descricao;
        this.dano = dano;
        this.cura = cura;
        this.preco = preco;
    }

    // esses metodos static sao so pra nao ter que digitar os valores
    // toda vez que precisamos criar o mesmo item de novo

    public static Item espadaFerro() {
        return new Item("Espada de Ferro", "Uma espada de ferro comum.", 8, 0, 50);
    }

    public static Item espadaDraco() {
        return new Item("Espada de Ferro Draco", "Lamina forjada por Korrin.", 16, 0, 120);
    }

    public static Item pocaoCura() {
        return new Item("Pocao de Cura", "Restaura 40 pontos de vida.", 0, 40, 30);
    }
}
