public class Personagem {
    String nome;
    Integer força;
    Integer inteligência;
    Integer agilidade;
    Integer Vida = 100;
    String espada = Item.espadaFerro().nome;
    
    
    public Personagem(String nome, Integer força, Integer inteligência, Integer agilidade, Integer Vida) {
        this.nome = nome;
        this.força = força;
        this.inteligência = inteligência;
        this.agilidade = agilidade;
        this.Vida = Vida;
        
    }
    public static Personagem Heroi = new Personagem("Herói", 10, 10, 10, 100);
    public static void registro( Personagem personagem) {
        System.out.println("Nome: " + personagem.nome);
        System.out.println("Força: " + personagem.força);
        System.out.println("Inteligência: " + personagem.inteligência);
        System.out.println("Agilidade: " + personagem.agilidade);
        System.out.println("Vida: " + personagem.Vida);
    }

    public void ganharForça(Integer quantidade) {
        força += quantidade;
    }
    public void ganharInteligência(Integer quantidade) {                                    
        inteligência += quantidade;
    }
    public void ganharAgilidade(Integer quantidade) {
        agilidade += quantidade;
    }

}
