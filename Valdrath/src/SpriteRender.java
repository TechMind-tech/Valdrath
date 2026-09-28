import java.awt.Color;                 // sabe lidar com cores (vermelho, verde, azul)
import java.awt.image.BufferedImage;   // guarda a imagem na memoria
import java.io.File;                   // representa o arquivo no disco
import javax.imageio.ImageIO;          // sabe abrir arquivos de imagem (.png)

/*
 * SpriteRender
 * ------------
 * Esta classe tem uma unica responsabilidade: pegar uma imagem .png e
 * "desenha-la" no terminal usando blocos coloridos.
 *
 * A ideia (bem de estudante): cada pixel da imagem vira dois espacos com
 * a COR DE FUNDO igual a cor do pixel. Assim a figura aparece no CMD como
 * um "pixel art" feito de retangulos coloridos.
 *
 * Por que espaco e nao um caractere de bloco? Porque o console do Windows
 * normalmente nao esta configurado em UTF-8. Se a gente tentasse imprimir
 * um caractere especial (tipo o bloco "█"), ele vira "?" na tela e o
 * sprite fica ilegivel. Espaco em branco e ASCII puro, entao sempre
 * funciona, em qualquer codepage do Windows.
 *
 * Para o resto do jogo nao precisar decorar o nome dos arquivos, criamos
 * o metodo mostrar("chave"), que traduz uma chave simples (ex: "lobo")
 * para o caminho do arquivo (ex: "sprites/lobo.png").
 */
public class SpriteRender {

    // O "ESC" e um sinal especial que avisa o terminal: "vem uma cor por ai".
    static final String ESC = "\u001B";

    // Pasta onde ficam todos os sprites do jogo.
    static final String PASTA = "sprites/";

    /*
     * mostrar(chave)
     * Recebe uma chave curta e desenha o sprite correspondente.
     * Antes de desenhar, limpamos a tela para a cena ficar organizada.
     */
    public static void mostrar(String chave) {
        limparTela();
        String arquivo = PASTA + traduzirChave(chave) + ".png";
        desenhar(arquivo);
    }

    /*
     * localizarArquivo(nomeArquivo)
     * O problema: "sprites/lobo.png" so funciona se o programa for
     * executado de dentro da pasta "Valdrath" (onde a pasta sprites
     * mora). Se alguem roda o jogo de outra pasta (ex: abrindo o VS
     * Code na pasta de cima do projeto), o caminho relativo nao acha
     * o arquivo e cai no aviso de "sprite indisponivel".
     *
     * Para resolver sem depender de configuracao, a gente monta uma
     * lista de "pastas candidatas" onde a pasta sprites poderia estar,
     * e testa uma por uma até achar o arquivo:
     *
     *   1) a pasta de onde o programa foi executado (user.dir) e as
     *      pastas acima dela (caso rodem de dentro de "bin", por
     *      exemplo)
     *   2) a pasta onde o .class do jogo realmente esta (isso o Java
     *      sabe de forma exata, nao depende de onde o comando "java"
     *      foi chamado) e as pastas acima dela
     *   3) dentro dessas pastas, tambem olhamos numa subpasta chamada
     *      "Valdrath", que e o caso de alguem rodar o jogo estando um
     *      nivel ACIMA da pasta do projeto
     */
    static File localizarArquivo(String nomeArquivo) {
        File direto = new File(nomeArquivo);
        if (direto.exists()) {
            return direto;
        }

        for (File pastaBase : pastasCandidatas()) {
            // tenta "pastaBase/sprites/arquivo.png"
            File tentativa = new File(pastaBase, nomeArquivo);
            if (tentativa.exists()) {
                return tentativa;
            }
            // tenta "pastaBase/Valdrath/sprites/arquivo.png"
            File tentativaFilha = new File(new File(pastaBase, "Valdrath"), nomeArquivo);
            if (tentativaFilha.exists()) {
                return tentativaFilha;
            }
        }

        // Nao achou em lugar nenhum: devolve o caminho original mesmo,
        // pra mensagem de erro mostrar o que a gente tentou abrir.
        return direto;
    }

    // Monta a lista de pastas onde vamos procurar "sprites/...".
    static java.util.List<File> pastasCandidatas() {
        java.util.List<File> pastas = new java.util.ArrayList<>();

        // Ponto 1: pasta de onde o comando "java" foi chamado.
        adicionarComPais(pastas, new File(System.getProperty("user.dir")));

        // Ponto 2: pasta onde o .class deste programa realmente esta
        // (ex: .../Valdrath/bin). Assim, mesmo que o comando "java"
        // tenha sido chamado de outro lugar, a gente acha o projeto.
        try {
            File origem = new File(SpriteRender.class.getProtectionDomain()
                    .getCodeSource().getLocation().toURI());
            adicionarComPais(pastas, origem);
        } catch (Exception e) {
            // Se nao conseguir descobrir, so ignora e segue com o que tem.
        }

        return pastas;
    }

    // Adiciona a pasta recebida e ate 3 pastas acima dela na lista.
    static void adicionarComPais(java.util.List<File> pastas, File pasta) {
        for (int i = 0; i < 4 && pasta != null; i++) {
            pastas.add(pasta);
            pasta = pasta.getParentFile();
        }
    }

    /*
     * traduzirChave(chave)
     * Como nao temos um arquivo para cada cena do roteiro, aqui a gente
     * "reaproveita" os sprites que existem na pasta. Cada chave do roteiro
     * aponta para o desenho mais parecido que temos.
     */
    static String traduzirChave(String chave) {
        switch (chave) {
            // --- personagens / NPCs ---
            case "heroi":                  return "heroi";
            case "npc_aldric":             return "npc_aldric";
            case "npc_aldric_verdadeiro":  return "npc_aldric";
            case "npc_brenna":             return "npc_brenna";
            case "npc_korrin":             return "npc_korrin";
            case "npc_lyra":               return "npc_lyra";
            case "npc_malachar":           return "npc_malachar";

            // --- inimigos comuns ---
            case "mob_lobo_sombrio":       return "lobo";
            case "mob_bandido":            return "guerreiro";
            case "mob_aldeao_corrompido":  return "slime";
            case "mob_esqueleto_guardiao": return "esqueleto";
            case "mob_espectro":           return "espectro";
            case "mob_cavaleiro_ordem":    return "guerreiro";

            // --- chefes (bosses) ---
            case "boss_aldric_verdadeiro": return "boss_dragao";
            case "boss_malachar":          return "mago";
            case "boss_guardiao_chama":    return "boss_dragao";

            // --- a Voz da Chama e seus efeitos ---
            case "efeito_voz_chama":       return "voz_da_chama";
            case "cenario_chama_eterna":   return "voz_da_chama";
            case "cena_destruicao_chama":  return "voz_da_chama";
            case "cena_absorver_chama":    return "voz_da_chama";
            case "cena_chama_apagando":    return "voz_da_chama";

            // --- itens ---
            case "item_diario":            return "pocao";
            case "item_assinatura_aldric": return "moeda";
            case "moeda":                  return "moeda";
            case "pocao":                  return "pocao";

            // Se nao tivermos sprite para a chave (ex: cenarios), usamos o
            // logo/voz como padrao para nunca quebrar o jogo.
            default:                       return "voz_da_chama";
        }
    }

    /*
     * limparTela()
     * Envia um comando para o terminal apagar o que estava escrito.
     * Deixa a proxima cena "limpa", como uma folha nova.
     */
    public static void limparTela() {
        System.out.print(ESC + "[2J" + ESC + "[H");
        System.out.flush();
    }

    /*
     * desenhar(caminho)
     * Abre o arquivo de imagem e percorre pixel por pixel (linha por linha).
     * Pixel transparente vira so espaco sem cor; pixel colorido vira dois
     * espacos com o FUNDO pintado da cor do pixel.
     */
    public static void desenhar(String caminho) {
        try {
            File arquivo = localizarArquivo(caminho);
            BufferedImage imagem = ImageIO.read(arquivo);

            // Percorre todas as linhas (y) e, dentro de cada linha, todas as colunas (x).
            for (int y = 0; y < imagem.getHeight(); y++) {
                for (int x = 0; x < imagem.getWidth(); x++) {

                    Color pixel = new Color(imagem.getRGB(x, y), true);

                    if (pixel.getAlpha() < 128) {
                        System.out.print("  ");            // pixel invisivel = espaco
                    } else {
                        int r = pixel.getRed();
                        int g = pixel.getGreen();
                        int b = pixel.getBlue();
                        // Pinta o FUNDO de dois espacos com a cor do pixel.
                        // Usamos espaco (nao o caractere de bloco "█") porque o
                        // console do Windows normalmente nao esta em UTF-8 e
                        // trocava o bloco por "?". Espaco e ASCII puro, entao
                        // funciona em qualquer codepage do Windows.
                        System.out.print(ESC + "[48;2;" + r + ";" + g + ";" + b + "m" + "  ");
                    }
                }
                System.out.println(ESC + "[0m");           // fim da linha: desliga a cor
            }

        } catch (Exception e) {
            // Se a imagem nao abrir, o jogo continua normalmente com um aviso.
            System.out.println("(sprite indisponivel: " + caminho + ")");
        }
    }
}
