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
 * A ideia (bem de estudante): cada pixel da imagem vira dois quadradinhos
 * "██" pintados com a mesma cor do pixel. Assim a figura aparece no CMD.
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
     * Pixel transparente vira espaco; pixel colorido vira dois blocos "██".
     */
    public static void desenhar(String caminho) {
        try {
            BufferedImage imagem = ImageIO.read(new File(caminho));

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
                        // Pinta dois blocos com a cor exata do pixel (formato RGB do terminal).
                        System.out.print(ESC + "[38;2;" + r + ";" + g + ";" + b + "m" + "██");
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
