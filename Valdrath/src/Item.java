public class Item {
    String nome;
    Integer quantidade;
    String descricao;
    Integer dano;
    Integer cura;
    Integer preco;

    public Item(String nome, Integer quantidade, String descricao, Integer dano, Integer cura, Integer preco) {
        this.nome = nome;
        this.quantidade = quantidade;
        this.descricao = descricao;
        this.dano = dano;
        this.cura = cura;
        this.preco = preco;
    }
    public static Item espadaFerro() {
    return new Item("Espada de Ferro", 1, "Uma espada de ferro comum.", 10, 0, 50);
}
    public static Item pocaoCura() {
    return new Item("Poção de Cura", 1, "Uma poção que restaura a vida do personagem.", 0, 20, 30);
}

    public static void equiparItem(Item item) {
        
        System.out.println("O item " + item.nome + " foi equipado!");
    }
}
