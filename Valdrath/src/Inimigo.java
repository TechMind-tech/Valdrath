public class Inimigo {
        String nome;
        Integer força;
        Integer inteligência;   
        Integer agilidade;
        Integer Vida;
        Integer Ouro;
        Integer Experiencia;
        Integer dano;


        
        public Inimigo(String nome, Integer força, Integer inteligência, Integer agilidade, Integer Vida) {
            this.nome = nome;
            this.força = força;
            this.inteligência = inteligência;
            this.agilidade = agilidade;
            this.Vida = Vida;
            this.Ouro = 0;
            this.Experiencia = 0;
            this.dano = 0;
        }
        static Inimigo loboSombrio() {
            return new Inimigo("Lobo Sombrio", 5, 5, 5, 100);
        
        }


        public Inimigo lobo = Inimigo.loboSombrio();
        
           static Inimigo ladrão() {
            return new Inimigo("Ladrão", 5, 5, 5, 100);
        }
      

        public void registro() {
            System.out.println("Nome: " + nome);
            System.out.println("Força: " + força);
            System.out.println("Inteligência: " + inteligência);
            System.out.println("Agilidade: " + agilidade);
            System.out.println("Vida: " + Vida);
            System.out.println("Ouro: " + Ouro);
            System.out.println("Experiência: " + Experiencia);
            System.out.println("Dano: " + dano);
        }
        
}
