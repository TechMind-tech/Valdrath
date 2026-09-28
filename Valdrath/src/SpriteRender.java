import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;

// Essa classe pega uma imagem .png da pasta sprites e desenha ela no
// terminal usando quadradinhos coloridos (cada pixel da imagem vira um
// "pixel" de cor no CMD).
public class SpriteRender {

    // codigo especial que avisa o terminal que vem uma cor
    static final String ESC = "\u001B";

    // pasta onde ficam as imagens
    static final String PASTA = "sprites/";

    // mostra o sprite da chave recebida (ex: "lobo", "npc_lyra")
    public static void mostrar(String chave) {
        limparTela();
        String arquivo = PASTA + traduzirChave(chave) + ".png";
        desenhar(arquivo);
    }

    // Fica procurando o arquivo em algumas pastas diferentes.
    // Isso resolve o problema de "sprite indisponivel" quando o jogo e
    // executado de uma pasta diferente (ex: da pasta de cima do projeto,
    // onde nao tem a pasta sprites direto).
    static File localizarArquivo(String nomeArquivo) {
        File direto = new File(nomeArquivo);
        if (direto.exists()) {
            return direto;
        }

        for (File pastaBase : pastasCandidatas()) {
            File tentativa = new File(pastaBase, nomeArquivo);
            if (tentativa.exists()) {
                return tentativa;
            }
            File tentativaFilha = new File(new File(pastaBase, "Valdrath"), nomeArquivo);
            if (tentativaFilha.exists()) {
                return tentativaFilha;
            }
        }

        return direto;
    }

    // monta a lista de pastas onde vamos tentar procurar a pasta sprites
    static List<File> pastasCandidatas() {
        List<File> pastas = new ArrayList<>();

        // pasta de onde o comando java foi chamado
        adicionarComPais(pastas, new File(System.getProperty("user.dir")));

        // pasta onde o .class deste programa realmente esta (ex: bin)
        try {
            File origem = new File(SpriteRender.class.getProtectionDomain()
                    .getCodeSource().getLocation().toURI());
            adicionarComPais(pastas, origem);
        } catch (Exception e) {
            // se nao der pra descobrir, so ignora
        }

        return pastas;
    }

    // coloca a pasta recebida e as 3 pastas acima dela na lista
    static void adicionarComPais(List<File> pastas, File pasta) {
        int i = 0;
        while (i < 4 && pasta != null) {
            pastas.add(pasta);
            pasta = pasta.getParentFile();
            i++;
        }
    }

    // como nao temos um desenho pra cada cena do roteiro, aqui a gente
    // reaproveita os sprites que ja existem na pasta
    static String traduzirChave(String chave) {
        switch (chave) {
            case "heroi":                  return "heroi";
            case "npc_aldric":             return "npc_aldric";
            case "npc_aldric_verdadeiro":  return "npc_aldric";
            case "npc_brenna":             return "npc_brenna";
            case "npc_korrin":             return "npc_korrin";
            case "npc_lyra":               return "npc_lyra";
            case "npc_malachar":           return "npc_malachar";

            case "mob_lobo_sombrio":       return "lobo";
            case "mob_bandido":            return "guerreiro";
            case "mob_aldeao_corrompido":  return "slime";
            case "mob_esqueleto_guardiao": return "esqueleto";
            case "mob_espectro":           return "espectro";
            case "mob_cavaleiro_ordem":    return "guerreiro";

            case "boss_aldric_verdadeiro": return "boss_dragao";
            case "boss_malachar":          return "mago";
            case "boss_guardiao_chama":    return "boss_dragao";

            case "efeito_voz_chama":       return "voz_da_chama";
            case "cenario_chama_eterna":   return "voz_da_chama";
            case "cena_destruicao_chama":  return "voz_da_chama";
            case "cena_absorver_chama":    return "voz_da_chama";
            case "cena_chama_apagando":    return "voz_da_chama";

            case "item_diario":            return "pocao";
            case "item_assinatura_aldric": return "moeda";
            case "moeda":                  return "moeda";
            case "pocao":                  return "pocao";

            // se nao existir sprite pra essa chave, usa um padrao
            default:                       return "voz_da_chama";
        }
    }

    // limpa a tela do terminal antes de desenhar a proxima cena
    public static void limparTela() {
        System.out.print(ESC + "[2J" + ESC + "[H");
        System.out.flush();
    }

    // abre a imagem e desenha ela pixel por pixel
    public static void desenhar(String caminho) {
        try {
            File arquivo = localizarArquivo(caminho);
            BufferedImage imagem = ImageIO.read(arquivo);

            for (int y = 0; y < imagem.getHeight(); y++) {
                for (int x = 0; x < imagem.getWidth(); x++) {

                    Color pixel = new Color(imagem.getRGB(x, y), true);

                    if (pixel.getAlpha() < 128) {
                        // pixel transparente, so deixa em branco
                        System.out.print("  ");
                    } else {
                        int r = pixel.getRed();
                        int g = pixel.getGreen();
                        int b = pixel.getBlue();
                        // pinta o FUNDO de 2 espacos com a cor do pixel
                        // (usamos espaco e nao um caractere especial porque
                        // o console do Windows normalmente nao mostra bem
                        // caracteres especiais, so vira "?")
                        System.out.print(ESC + "[48;2;" + r + ";" + g + ";" + b + "m" + "  ");
                    }
                }
                System.out.println(ESC + "[0m");
            }

        } catch (Exception e) {
            // se a imagem nao abrir por algum motivo, o jogo continua
            System.out.println("(sprite indisponivel: " + caminho + ")");
        }
    }
}
