import java.awt.Color;                 // sabe lidar com cores (vermelho, verde, azul)
import java.awt.image.BufferedImage;   // guarda a imagem na memória
import java.io.File;                   // representa o arquivo no disco
import javax.imageio.ImageIO;          // sabe abrir arquivos de imagem (.png)

public class SpriteRender {

    static final String ESC = "\u001B"; // o "sinal" que avisa o terminal que vem uma cor

    public static void desenhar(String caminho) {
        try {
            BufferedImage imagem = ImageIO.read(new File(caminho));

            for (int y = 0; y < imagem.getHeight(); y++) {
                for (int x = 0; x < imagem.getWidth(); x++) {

                    Color pixel = new Color(imagem.getRGB(x, y), true);

                    if (pixel.getAlpha() < 128) {
                        System.out.print("  ");            // pixel invisível = espaço
                    } else {
                        int r = pixel.getRed();
                        int g = pixel.getGreen();
                        int b = pixel.getBlue();
                        System.out.print(ESC + "[38;2;" + r + ";" + g + ";" + b + "m" + "██");
                    }
                }
                System.out.println(ESC + "[0m");           // fim da linha: desliga a cor
            }

        } catch (Exception e) {
            System.out.println("(não consegui abrir a imagem: " + caminho + ")");
        }
    }
}