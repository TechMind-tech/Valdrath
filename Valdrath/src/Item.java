/*
 * Item
 * ----
 * Representa qualquer objeto que o heroi possa usar: uma espada (da dano)
 * ou uma pocao (cura vida). Mantemos a classe bem simples: so guarda os
 * dados e oferece "fabricas" (metodos static) para criar itens prontos.
 */
public class Item {
    String nome;         // nome que aparece na tela
    String descricao;    // texto explicativo
    int dano;            // quanto de dano a mais este item concede num ataque
    int cura;            // quanto de vida a pocao devolve
    int preco;           // custo em ouro na loja

    // Construtor: usado quando criamos um item novo.
    public Item(String nome, String descricao, int dano, int cura, int preco) {
        this.nome = nome;
        this.descricao = descricao;
        this.dano = dano;
        this.cura = cura;
        this.preco = preco;
    }

    // Fabrica de uma espada comum (arma inicial do heroi).
    public static Item espadaFerro() {
        return new Item("Espada de Ferro", "Uma espada de ferro comum.", 8, 0, 50);
    }

    // Fabrica de uma espada melhor, vendida na loja.
    public static Item espadaDraco() {
        return new Item("Espada de Ferro Draco", "Lamina forjada por Korrin.", 16, 0, 120);
    }

    // Fabrica de uma pocao de cura.
    public static Item pocaoCura() {
        return new Item("Pocao de Cura", "Restaura 40 pontos de vida.", 0, 40, 30);
    }
}
