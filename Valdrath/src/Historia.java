import java.util.Scanner;

/*
 * Historia
 * --------
 * Aqui vive TODO o roteiro de "As Cinzas de Valdrath": prologo, os quatro atos,
 * as cinco escolhas com karma e os tres finais (Heroi, Vilao, Neutro).
 *
 * Como funciona:
 *   - jogar(scanner) e o ponto de entrada: chama o prologo, os atos em ordem
 *     e, no fim, decide o final pelo karma acumulado.
 *   - O estado (honra, ambicao, flags) fica em campos static desta classe,
 *     zerados no comeco de cada partida por resetarEstado().
 *   - Cada cena e um metodo privado, com as caixas de dialogo do roteiro.
 *
 * As batalhas usam a classe Batalha; os sprites usam SpriteRender.mostrar(chave).
 */
public class Historia {

    // ----------------- ESTADO DA PARTIDA -----------------

    // Karma (portas dos finais: >= 3 no lado dominante).
    static int honra;
    static int ambicao;

    // Flags de narrativa.
    static boolean pistaDesertor;      // ouviu o bandido no Ato 1
    static boolean vilaSalva;          // doou para Brenna
    static boolean vilaSaqueada;       // saqueou Greywatch
    static boolean lyraNoGrupo;        // Lyra viaja com voce
    static boolean lyraAliada;         // voce a protegeu
    static boolean lyraTraida;         // voce a denunciou
    static boolean lyraFilhaRevelada;  // ela contou que e filha de Malachar
    static boolean verdadeAceita;      // aceitou a verdade no Ato 3
    static boolean verdadeNegada;      // recusou-se a acreditar
    static boolean pactoVoz;           // cobicou a Chama
    static boolean malacharAliado;     // Malachar sobe com voce
    static int confiancaLyra;          // termometro interno (nunca mostrado)

    // Reinicia o estado no comeco de cada nova partida.
    static void resetarEstado() {
        honra = 0;
        ambicao = 0;
        pistaDesertor = false;
        vilaSalva = false;
        vilaSaqueada = false;
        lyraNoGrupo = false;
        lyraAliada = false;
        lyraTraida = false;
        lyraFilhaRevelada = false;
        verdadeAceita = false;
        verdadeNegada = false;
        pactoVoz = false;
        malacharAliado = false;
        confiancaLyra = 0;
    }

    // ----------------- UTILITARIOS DE CENA -----------------

    // Espera o jogador apertar ENTER para seguir (ritmo de leitura).
    static void aguardar(Scanner scanner) {
        scanner.nextLine();
    }

    // Mostra um sprite (limpa a tela e desenha). Nunca quebra: chaves
    // desconhecidas caem no sprite padrao dentro de SpriteRender.
    static void sprite(String chave) {
        SpriteRender.mostrar(chave);
    }

    // Le uma escolha numerica valida (entre 1 e maxOpcao). Repete ate acertar.
    static int lerEscolha(Scanner scanner, int maxOpcao) {
        while (true) {
            System.out.print("   > ");
            String entrada = scanner.nextLine().trim();
            try {
                int v = Integer.parseInt(entrada);
                if (v >= 1 && v <= maxOpcao) {
                    return v;
                }
            } catch (NumberFormatException e) {
                // cai no aviso abaixo
            }
            System.out.println("   (Escolha um numero entre 1 e " + maxOpcao + ".)");
        }
    }

    // ===================================================================
    //  PONTO DE ENTRADA
    // ===================================================================

    public static void jogar(Scanner scanner) {
        resetarEstado();

        // O heroi comeca com atributos base; o nome vem no prologo.
        Personagem jogador = new Personagem("Recruta", 12, 8, 10, 100);

        prologo(scanner, jogador);
        ato1(scanner, jogador);
        ato2(scanner, jogador);
        ato3(scanner, jogador);
        ato4(scanner, jogador);
    }

    // ===================================================================
    //  PROLOGO — A CIDADELA DA AURORA
    // ===================================================================

    static void prologo(Scanner scanner, Personagem jogador) {
        sprite("cenario_chama_eterna");
        System.out.println(
                " .~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
                " :                                            :\n" +
                " :   Mil anos atras, algo caiu do ceu sobre   :\n" +
                " :   Valdrath.                                :\n" +
                " :                                            :\n" +
                " :   Nao era pedra. Nao era estrela.          :\n" +
                " :   Era fogo que nao apaga.                  :\n" +
                " :                                            :\n" +
                " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
        aguardar(scanner);

        System.out.println(
                " .~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
                " :                                            :\n" +
                " :   Chamaram de Chama Eterna.                :\n" +
                " :   Construiram uma cidadela em volta dela e :\n" +
                " :   uma ordem de cavaleiros para guarda-la.  :\n" +
                " :   Enquanto ela arder, dizem, o reino vive. :\n" +
                " :                                            :\n" +
                " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
        aguardar(scanner);

        System.out.println(
                " .~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
                " :                                            :\n" +
                " :   Hoje ela ainda arde.                     :\n" +
                " :   E mesmo assim os rios secam, as          :\n" +
                " :   plantacoes apodrecem e aldeias inteiras  :\n" +
                " :   adoecem sem motivo.                      :\n" +
                " :   A Ordem chama isso de praga.             :\n" +
                " :   A Ordem tem um culpado.                  :\n" +
                " :                                            :\n" +
                " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
        aguardar(scanner);

        sprite("npc_aldric");
        System.out.println(
                " .~~[ ALDRIC ]~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
                " :                                            :\n" +
                " :   Levante a cabeca, recruta. Hoje voce     :\n" +
                " :   deixa de ser aprendiz.                   :\n" +
                " :                                            :\n" +
                " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
        aguardar(scanner);

        System.out.println(
                " .~~[ ALDRIC ]~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
                " :                                            :\n" +
                " :   Voce conhece o nome dele. Malachar, o    :\n" +
                " :   Feiticeiro Sombrio. Foi um dos nossos,   :\n" +
                " :   um dia. O melhor de nos.                 :\n" +
                " :                                            :\n" +
                " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
        aguardar(scanner);

        System.out.println(
                " .~~[ ALDRIC ]~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
                " :                                            :\n" +
                " :   Ele traiu a Ordem, roubou o que sabia e  :\n" +
                " :   lancou esta praga sobre Valdrath por     :\n" +
                " :   rancor. Faz vinte anos que o povo paga   :\n" +
                " :   por isso.                                :\n" +
                " :                                            :\n" +
                " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
        aguardar(scanner);

        System.out.println(
                " .~~[ ALDRIC ]~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
                " :                                            :\n" +
                " :   Quatro regioes separam esta cidadela da  :\n" +
                " :   torre dele. Thornwood, Greywatch, as     :\n" +
                " :   ruinas de Karn. E o caminho de volta ate :\n" +
                " :   aqui.                                    :\n" +
                " :                                            :\n" +
                " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
        aguardar(scanner);

        System.out.println(
                " .~~[ ALDRIC ]~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
                " :                                            :\n" +
                " :   Atravesse todas. Encontre Malachar. E    :\n" +
                " :   acabe com ele.                           :\n" +
                " :                                            :\n" +
                " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
        aguardar(scanner);

        System.out.println(
                " .~~[ ALDRIC ]~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
                " :                                            :\n" +
                " :   Eu te criei para isto, garoto. Nao me    :\n" +
                " :   faca duvidar.                            :\n" +
                " :                                            :\n" +
                " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
        aguardar(scanner);

        // Nome do jogador (importante para o impacto do Ato 3).
        System.out.print("   Seu nome, recruta: ");
        String nome = scanner.nextLine().trim();
        if (nome.isEmpty()) {
            nome = "Recruta";
        }
        jogador.nome = nome;

        System.out.println(
                " .~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
                " :                                            :\n" +
                " :   Voce aperta o cinto da espada e desce os :\n" +
                " :   degraus da Cidadela pela ultima vez como :\n" +
                " :   alguem que acredita em tudo isso.        :\n" +
                " :                                            :\n" +
                " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
        aguardar(scanner);
    }

    // ===================================================================
    //  ATO 1 — A FLORESTA DE THORNWOOD
    // ===================================================================

    static void ato1(Scanner scanner, Personagem jogador) {
        // A1-01 Chegada
        sprite("cenario_thornwood");
        System.out.println(
                " .~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
                " :                                            :\n" +
                " :   Thornwood comeca onde a estrada acaba.   :\n" +
                " :   As arvores aqui cresceram tortas, como   :\n" +
                " :   se tivessem tentado fugir e desistido no :\n" +
                " :   meio do caminho.                         :\n" +
                " :                                            :\n" +
                " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
        aguardar(scanner);

        System.out.println(
                " .~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
                " :                                            :\n" +
                " :   Nao ha passaros.                         :\n" +
                " :   Ha um cheiro doce e errado no ar, o      :\n" +
                " :   cheiro de fruta que passou do ponto.     :\n" +
                " :                                            :\n" +
                " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
        aguardar(scanner);

        // A1-02 Lobo Sombrio
        sprite("mob_lobo_sombrio");
        System.out.println(
                " .~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
                " :                                            :\n" +
                " :   Algo se move entre os troncos.           :\n" +
                " :   Baixo, rapido, e maior do que devia ser. :\n" +
                " :                                            :\n" +
                " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
        aguardar(scanner);

        Batalha.iniciar(scanner, jogador, Inimigo.loboSombrio());
        Batalha.garantirSobrevivencia(jogador);

        System.out.println(
                " .~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
                " :                                            :\n" +
                " :   O corpo cai e fica quieto.               :\n" +
                " :   Sob o pelo apodrecido, veias escuras     :\n" +
                " :   subiam em direcao ao coracao. O bicho ja :\n" +
                " :   estava sendo comido por dentro.          :\n" +
                " :                                            :\n" +
                " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
        aguardar(scanner);

        // A1-03 Lyra aparece
        sprite("npc_lyra");
        System.out.println(
                " .~~[ LYRA ]~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
                " :                                            :\n" +
                " :   Devagar com essa espada. Se eu quisesse  :\n" +
                " :   te matar, voce ja estaria caido ai do    :\n" +
                " :   lado do cachorro.                        :\n" +
                " :                                            :\n" +
                " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
        aguardar(scanner);

        System.out.println(
                " .~~[ LYRA ]~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
                " :                                            :\n" +
                " :   Lyra. Ladra, guia, e a unica pessoa      :\n" +
                " :   nesta floresta que ainda tem os dois     :\n" +
                " :   olhos funcionando.                       :\n" +
                " :                                            :\n" +
                " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
        aguardar(scanner);

        System.out.println(
                " .~~[ LYRA ]~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
                " :                                            :\n" +
                " :   Vou com voce ate Greywatch. Tenho contas :\n" +
                " :   a acertar com a sua Ordem, e voce tem    :\n" +
                " :   uma escolta de graca.                    :\n" +
                " :                                            :\n" +
                " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
        aguardar(scanner);

        lyraNoGrupo = true;

        // A1-04 Bandido Desertor
        sprite("mob_bandido");
        System.out.println(
                " .~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
                " :                                            :\n" +
                " :   Ele pula da moita gritando, com uma      :\n" +
                " :   espada da Ordem enferrujada e um brasao  :\n" +
                " :   arrancado do peito.                      :\n" +
                " :                                            :\n" +
                " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
        aguardar(scanner);

        Batalha.iniciar(scanner, jogador, Inimigo.bandidoDesertor());
        Batalha.garantirSobrevivencia(jogador);

        System.out.println(
                " .~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
                " :                                            :\n" +
                " :   O homem cai de joelhos, sem ar, com a    :\n" +
                " :   lamina a um palmo da garganta.           :\n" +
                " :   Ele so levanta as maos e espera.         :\n" +
                " :                                            :\n" +
                " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
        aguardar(scanner);

        System.out.println(
                " .~~[ BANDIDO ]~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
                " :                                            :\n" +
                " :   Faz. Faz logo. E pra isso que eles te    :\n" +
                " :   mandaram, nao e.                         :\n" +
                " :                                            :\n" +
                " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
        aguardar(scanner);

        System.out.println(
                " .~~[ LYRA ]~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
                " :                                            :\n" +
                " :   Ele e seu. Eu so assisto.                :\n" +
                " :                                            :\n" +
                " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
        aguardar(scanner);

        // ESCOLHA 1
        System.out.println();
        System.out.println("   ESCOLHA 1 — O bandido rendido");
        System.out.println("   [1] Abaixar a espada e ouvir o que ele tem a dizer");
        System.out.println("   [2] Executar em nome da Ordem");
        int e1 = lerEscolha(scanner, 2);

        if (e1 == 1) {
            honra++;
            pistaDesertor = true;
            confiancaLyra++;
            cenaPouparBandido(scanner);
        } else {
            ambicao++;
            confiancaLyra--;
            cenaExecutarBandido(scanner);
        }

        // A1-07 Fim do Ato 1
        sprite("cenario_thornwood_saida");
        System.out.println(
                " .~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
                " :                                            :\n" +
                " :   A trilha desce e Thornwood fica para     :\n" +
                " :   tras.                                    :\n" +
                " :   Ao longe, no vale, luzes fracas:         :\n" +
                " :   Greywatch.                               :\n" +
                " :                                            :\n" +
                " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
        aguardar(scanner);

        System.out.println(
                " .~~[ LYRA ]~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
                " :                                            :\n" +
                " :   Prepara o estomago. Greywatch nao esta   :\n" +
                " :   doente. Greywatch esta sendo comida.     :\n" +
                " :                                            :\n" +
                " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
        aguardar(scanner);
    }

    static void cenaPouparBandido(Scanner scanner) {
        System.out.println(
                " .~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
                " :                                            :\n" +
                " :   Voce abaixa a lamina.                    :\n" +
                " :   O homem pisca duas vezes, como se a      :\n" +
                " :   misericordia fosse uma armadilha.        :\n" +
                " :                                            :\n" +
                " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
        aguardar(scanner);

        System.out.println(
                " .~~[ BANDIDO ]~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
                " :                                            :\n" +
                " :   Ninguem fala do que tem la embaixo, nas  :\n" +
                " :   catacumbas de Karn. Eu vi. Vi o que a    :\n" +
                " :   Ordem leva pra dentro daquele salao e    :\n" +
                " :   nao traz de volta.                       :\n" +
                " :                                            :\n" +
                " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
        aguardar(scanner);

        System.out.println(
                " .~~[ BANDIDO ]~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
                " :                                            :\n" +
                " :   A praga nao vem de fora, garoto. Ela vem :\n" +
                " :   de casa.                                 :\n" +
                " :                                            :\n" +
                " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
        aguardar(scanner);

        System.out.println(
                " .~~[ LYRA ]~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
                " :                                            :\n" +
                " :   Interessante. Um louco a menos no mundo, :\n" +
                " :   ou uma verdade a mais. Voce vai ter que  :\n" +
                " :   decidir qual dos dois foi.               :\n" +
                " :                                            :\n" +
                " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
        aguardar(scanner);
    }

    static void cenaExecutarBandido(Scanner scanner) {
        System.out.println(
                " .~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
                " :                                            :\n" +
                " :   Voce nao pergunta nada.                  :\n" +
                " :   A Ordem nao pergunta.                    :\n" +
                " :                                            :\n" +
                " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
        aguardar(scanner);

        System.out.println(
                " .~~[ LYRA ]~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
                " :                                            :\n" +
                " :   Rapido. Limpo. Voce nem quis saber o     :\n" +
                " :   nome dele. Anota isso: o dia em que voce :\n" +
                " :   teve a chance de ouvir e escolheu nao.   :\n" +
                " :                                            :\n" +
                " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
        aguardar(scanner);
    }

    // ===================================================================
    //  ATO 2 — A VILA DE GREYWATCH
    // ===================================================================

    static void ato2(Scanner scanner, Personagem jogador) {
        // A2-01 Chegada
        sprite("cenario_greywatch");
        System.out.println(
                " .~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
                " :                                            :\n" +
                " :   Greywatch tem trinta casas e nenhuma     :\n" +
                " :   fumaca saindo das chamines.              :\n" +
                " :   Nao porque nao ha fogo. Porque nao ha    :\n" +
                " :   ninguem em pe para acende-lo.            :\n" +
                " :                                            :\n" +
                " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
        aguardar(scanner);

        // A2-02 Brenna
        sprite("npc_brenna");
        System.out.println(
                " .~~[ BRENNA ]~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
                " :                                            :\n" +
                " :   Se veio da Cidadela, chegou tarde. Se    :\n" +
                " :   veio ajudar, entre e lave as maos.       :\n" +
                " :                                            :\n" +
                " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
        aguardar(scanner);

        System.out.println(
                " .~~[ BRENNA ]~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
                " :                                            :\n" +
                " :   Brenna. Fui parteira desta vila por      :\n" +
                " :   dezessete anos. Agora sou so a pessoa    :\n" +
                " :   que segura a mao deles no fim.           :\n" +
                " :                                            :\n" +
                " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
        aguardar(scanner);

        System.out.println(
                " .~~[ BRENNA ]~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
                " :                                            :\n" +
                " :   Mandamos tres pedidos de socorro pra     :\n" +
                " :   Ordem. Voltou um cavaleiro. Ele contou   :\n" +
                " :   quantos ainda estavam vivos, anotou e    :\n" +
                " :   foi embora.                              :\n" +
                " :                                            :\n" +
                " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
        aguardar(scanner);

        // A2-03 Aldeao Corrompido
        sprite("mob_aldeao_corrompido");
        System.out.println(
                " .~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
                " :                                            :\n" +
                " :   O que sai da despensa ja foi um homem.   :\n" +
                " :   As veias pretas chegaram nos olhos e     :\n" +
                " :   continuaram subindo.                     :\n" +
                " :                                            :\n" +
                " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
        aguardar(scanner);

        System.out.println(
                " .~~[ BRENNA ]~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
                " :                                            :\n" +
                " :   Esse e o Halden. Ele consertou meu       :\n" +
                " :   telhado no inverno passado... faz        :\n" +
                " :   rapido. Por favor. Faz rapido.           :\n" +
                " :                                            :\n" +
                " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
        aguardar(scanner);

        Batalha.iniciar(scanner, jogador, Inimigo.aldeaoCorrompido());
        Batalha.garantirSobrevivencia(jogador);

        System.out.println(
                " .~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
                " :                                            :\n" +
                " :   Quando acaba, Brenna cobre o rosto dele. :\n" +
                " :   Ela nao chora. Ela ja gastou isso.       :\n" +
                " :                                            :\n" +
                " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
        aguardar(scanner);

        honra++; // matar o corrompido e misericordia

        // ESCOLHA 2
        System.out.println();
        System.out.println("   ESCOLHA 2 — A vila faminta");
        System.out.println("   Ouro: " + jogador.ouro + " | Pocoes: " + jogador.pocoes);
        System.out.println("   [1] Entregar seu ouro e suas pocoes a Brenna");
        System.out.println("   [2] Guardar seus recursos e seguir a missao");
        System.out.println("   [3] Levar o que ainda tem valor nesta vila");
        int e2 = lerEscolha(scanner, 3);

        switch (e2) {
            case 1:
                honra += 2;
                vilaSalva = true;
                confiancaLyra++;
                jogador.zerarOuro();
                jogador.gastarPocoes();
                cenaDoar(scanner);
                break;
            case 2:
                cenaIgnorar(scanner);
                break;
            case 3:
                ambicao += 2;
                vilaSaqueada = true;
                confiancaLyra -= 2;
                jogador.saquearVila();
                cenaSaquear(scanner);
                break;
        }

        // A2-07 Korrin + Loja
        sprite("npc_korrin");
        System.out.println(
                " .~~[ KORRIN ]~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
                " :                                            :\n" +
                " :   Korrin. Ferro, lamina, remendo e frasco. :\n" +
                " :   Nao faco fiado, nao faco caridade e nao  :\n" +
                " :   faco perguntas.                          :\n" +
                " :                                            :\n" +
                " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
        aguardar(scanner);

        Loja.abrirLoja(scanner, jogador);

        System.out.println(
                " .~~[ KORRIN ]~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
                " :                                            :\n" +
                " :   Rumor de graca: eu vendo pra Ordem faz   :\n" +
                " :   trinta anos. O Grao-Mestre me atendeu na :\n" +
                " :   primeira vez e na semana passada. Mesmo  :\n" +
                " :   rosto. Mesmo cabelo branco.              :\n" +
                " :                                            :\n" +
                " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
        aguardar(scanner);

        System.out.println(
                " .~~[ KORRIN ]~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
                " :                                            :\n" +
                " :   Deve ser o filho, ne.                    :\n" +
                " :   Tem que ser o filho.                     :\n" +
                " :                                            :\n" +
                " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
        aguardar(scanner);

        // A2-08 O segredo de Lyra
        sprite("npc_lyra");
        System.out.println(
                " .~~[ LYRA ]~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
                " :                                            :\n" +
                " :   Faz onze anos. Uma aldeia chamada        :\n" +
                " :   Emberfall. A Ordem chegou dizendo que a  :\n" +
                " :   praga tinha pegado la. Isolaram tudo.    :\n" +
                " :   Levaram nove dias.                       :\n" +
                " :                                            :\n" +
                " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
        aguardar(scanner);

        System.out.println(
                " .~~[ LYRA ]~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
                " :                                            :\n" +
                " :   Quando abriram, nao tinha doente nenhum. :\n" +
                " :   Tinha vazio. Cento e quarenta pessoas de :\n" +
                " :   vazio. Minha mae estava la dentro.       :\n" +
                " :                                            :\n" +
                " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
        aguardar(scanner);

        System.out.println(
                " .~~[ LYRA ]~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
                " :                                            :\n" +
                " :   Agora voce sabe. Faz o que a Ordem te    :\n" +
                " :   ensinaria a fazer, ou faz outra coisa.   :\n" +
                " :                                            :\n" +
                " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
        aguardar(scanner);

        // ESCOLHA 3
        System.out.println();
        System.out.println("   ESCOLHA 3 — O segredo de Lyra");
        System.out.println("   [1] Eu acredito em voce. Ninguem vai saber por mim.");
        System.out.println("   [2] Mandar um corvo a Cidadela relatando a ladra");
        int e3 = lerEscolha(scanner, 2);

        if (e3 == 1) {
            honra++;
            lyraAliada = true;
            confiancaLyra += 2;
            cenaProtegerLyra(scanner);
        } else {
            ambicao += 2;
            lyraTraida = true;
            lyraNoGrupo = false;
            cenaDenunciarLyra(scanner);
        }
    }

    static void cenaDoar(Scanner scanner) {
        System.out.println(
                " .~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
                " :                                            :\n" +
                " :   Voce esvazia a bolsa em cima da mesa.    :\n" +
                " :   Nao e o bastante. Nunca ia ser. Mas e    :\n" +
                " :   tudo.                                    :\n" +
                " :                                            :\n" +
                " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
        aguardar(scanner);

        System.out.println(
                " .~~[ BRENNA ]~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
                " :                                            :\n" +
                " :   Foi mais do que a Ordem inteira fez em   :\n" +
                " :   vinte anos.                              :\n" +
                " :                                            :\n" +
                " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
        aguardar(scanner);
    }

    static void cenaIgnorar(Scanner scanner) {
        System.out.println(
                " .~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
                " :                                            :\n" +
                " :   Voce fecha a bolsa. A missao e maior que :\n" +
                " :   onze pessoas, voce diria em voz alta, se :\n" +
                " :   alguem perguntasse. Ninguem pergunta.    :\n" +
                " :                                            :\n" +
                " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
        aguardar(scanner);

        System.out.println(
                " .~~[ BRENNA ]~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
                " :                                            :\n" +
                " :   Entendo. Voce tem uma torre pra          :\n" +
                " :   alcancar.                                :\n" +
                " :                                            :\n" +
                " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
        aguardar(scanner);
    }

    static void cenaSaquear(Scanner scanner) {
        System.out.println(
                " .~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
                " :                                            :\n" +
                " :   Voce comeca pelo deposito. Depois as     :\n" +
                " :   casas cujos donos nao conseguem mais     :\n" +
                " :   levantar a cabeca do travesseiro.        :\n" +
                " :                                            :\n" +
                " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
        aguardar(scanner);

        System.out.println(
                " .~~[ BRENNA ]~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
                " :                                            :\n" +
                " :   Sai. Sai da minha vila.                  :\n" +
                " :                                            :\n" +
                " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
        aguardar(scanner);

        System.out.println(
                " .~~[ LYRA ]~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
                " :                                            :\n" +
                " :   Eu roubo pra viver, garoto. Ate eu tenho :\n" +
                " :   um andar de baixo. Voce acabou de cavar  :\n" +
                " :   mais um.                                 :\n" +
                " :                                            :\n" +
                " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
        aguardar(scanner);
    }

    static void cenaProtegerLyra(Scanner scanner) {
        System.out.println(
                " .~~[ VOCE ]~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
                " :                                            :\n" +
                " :   Eu acredito em voce.                     :\n" +
                " :                                            :\n" +
                " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
        aguardar(scanner);

        System.out.println(
                " .~~[ LYRA ]~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
                " :                                            :\n" +
                " :   Voce e pessimo cavaleiro, sabia. Fica    :\n" +
                " :   perto de mim em Karn. Tem coisa la       :\n" +
                " :   embaixo que eu preciso te mostrar.       :\n" +
                " :                                            :\n" +
                " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
        aguardar(scanner);
    }

    static void cenaDenunciarLyra(Scanner scanner) {
        System.out.println(
                " .~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
                " :                                            :\n" +
                " :   O corvo sobe antes do amanhecer, com o   :\n" +
                " :   selo da Ordem no tubo.                   :\n" +
                " :                                            :\n" +
                " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
        aguardar(scanner);

        System.out.println(
                " .~~[ LYRA ]~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
                " :                                            :\n" +
                " :   Eu te contei da minha mae. Boa sorte em  :\n" +
                " :   Karn, cavaleiro. Foi voce que escolheu   :\n" +
                " :   descer sozinho.                          :\n" +
                " :                                            :\n" +
                " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
        aguardar(scanner);
    }

    // ===================================================================
    //  ATO 3 — AS RUINAS DE KARN
    // ===================================================================

    static void ato3(Scanner scanner, Personagem jogador) {
        // A3-01 A descida
        sprite("cenario_karn");
        System.out.println(
                " .~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
                " :                                            :\n" +
                " :   Karn foi um mosteiro da Ordem antes de   :\n" +
                " :   ser ruina. A entrada das catacumbas esta :\n" +
                " :   selada por dentro para fora.             :\n" +
                " :                                            :\n" +
                " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
        aguardar(scanner);

        if (lyraAliada) {
            System.out.println(
                    " .~~[ LYRA ]~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
                    " :                                            :\n" +
                    " :   Terceira pedra da fileira de baixo.      :\n" +
                    " :   Empurra, nao puxa. Eu ja estive aqui.    :\n" +
                    " :                                            :\n" +
                    " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
            aguardar(scanner);
        } else {
            jogador.receberDano(8);
            System.out.println(
                    " .~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
                    " :                                            :\n" +
                    " :   Voce arromba a passagem sozinho. Custa   :\n" +
                    " :   caro: a pedra cede sobre o seu ombro.    :\n" +
                    " :                                            :\n" +
                    " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
            aguardar(scanner);
        }

        // A3-03 Esqueleto Guardiao
        sprite("mob_esqueleto_guardiao");
        System.out.println(
                " .~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
                " :                                            :\n" +
                " :   Dezenas de esqueletos de armadura        :\n" +
                " :   completa. Armadura da Ordem, brasao da   :\n" +
                " :   Aurora no peito. O do meio levanta a     :\n" +
                " :   cabeca.                                  :\n" +
                " :                                            :\n" +
                " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
        aguardar(scanner);

        Batalha.iniciar(scanner, jogador, Inimigo.esqueletoGuardiao());
        Batalha.garantirSobrevivencia(jogador);

        System.out.println(
                " .~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
                " :                                            :\n" +
                " :   No elmo, um nome e uma data. A data e de :\n" +
                " :   quatro anos atras. Este homem foi        :\n" +
                " :   enterrado de pe, para montar guarda      :\n" +
                " :   depois de morto.                         :\n" +
                " :                                            :\n" +
                " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
        aguardar(scanner);

        // A3-04 O diario de Malachar
        sprite("item_diario");
        System.out.println(
                " .~~[ DIARIO DE MALACHAR ]~~~~~~~~~~~~~~~~~~~~.\n" +
                " :                                            :\n" +
                " :   Ano 12. A Chama nao protege Valdrath. A  :\n" +
                " :   Chama se alimenta de Valdrath. A praga   :\n" +
                " :   vem do centro para as bordas.            :\n" +
                " :                                            :\n" +
                " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
        aguardar(scanner);

        System.out.println(
                " .~~[ DIARIO DE MALACHAR ]~~~~~~~~~~~~~~~~~~~~.\n" +
                " :                                            :\n" +
                " :   Ano 15. Perguntei ao Aldric. Ele nao     :\n" +
                " :   negou. Sorriu e perguntou quantos anos   :\n" +
                " :   eu achava que ele tinha.                 :\n" +
                " :                                            :\n" +
                " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
        aguardar(scanner);

        System.out.println(
                " .~~[ DIARIO DE MALACHAR ]~~~~~~~~~~~~~~~~~~~~.\n" +
                " :                                            :\n" +
                " :   Ano 16. Se alguem ler isto: nao me       :\n" +
                " :   procure para me matar. Me procure para   :\n" +
                " :   terminar o que eu comecei.               :\n" +
                " :                                            :\n" +
                " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
        aguardar(scanner);

        // A3-05 Espectro dos Sacrificados
        sprite("mob_espectro");
        System.out.println(
                " .~~[ ESPECTRO ]~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
                " :                                            :\n" +
                " :   Nos fomos oferecidos. Emberfall.         :\n" +
                " :   Hollowmere. Ashford. Duskrow. Diga os    :\n" +
                " :   nomes. Alguem tem que dizer os nomes.    :\n" +
                " :                                            :\n" +
                " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
        aguardar(scanner);

        Batalha.iniciar(scanner, jogador, Inimigo.espectroDosSacrificados());
        Batalha.garantirSobrevivencia(jogador);

        System.out.println(
                " .~~[ ESPECTRO ]~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
                " :                                            :\n" +
                " :   Duskrow. Voce conhece essa.              :\n" +
                " :   Voce nasceu nela.                        :\n" +
                " :                                            :\n" +
                " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
        aguardar(scanner);

        // A3-06 A parede dos nomes
        sprite("cenario_parede_nomes");
        System.out.println(
                " .~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
                " :                                            :\n" +
                " :   A ultima camara tem uma parede. Nela,    :\n" +
                " :   gravados em coluna, milhares de nomes,   :\n" +
                " :   agrupados por aldeia e por ano.          :\n" +
                " :                                            :\n" +
                " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
        aguardar(scanner);

        System.out.println(
                " .~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
                " :                                            :\n" +
                " :   Voce encontra Duskrow, ano dezenove. E   :\n" +
                " :   dentro de Duskrow, o seu sobrenome. Tres :\n" +
                " :   vezes. Pai. Mae. Irma.                   :\n" +
                " :                                            :\n" +
                " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
        aguardar(scanner);

        sprite("item_assinatura_aldric");
        System.out.println(
                " .~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
                " :                                            :\n" +
                " :   A assinatura no rodape da coluna:        :\n" +
                " :   Grao-Mestre Aldric da Aurora.            :\n" +
                " :   Sua familia nao morreu de praga. Sua     :\n" +
                " :   familia foi entregue.                    :\n" +
                " :                                            :\n" +
                " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
        aguardar(scanner);

        // A3-07 A Voz da Chama
        sprite("efeito_voz_chama");
        System.out.println(
                " .~~[ A VOZ DA CHAMA ]~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
                " :                                            :\n" +
                " :   Voce esta com raiva. Que bom. Raiva e    :\n" +
                " :   combustivel de qualidade.                :\n" +
                " :   Por que destruir, se voce pode possuir?  :\n" +
                " :                                            :\n" +
                " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
        aguardar(scanner);

        // A3-08 Lyra confessa (se aliada)
        if (lyraAliada) {
            sprite("npc_lyra");
            System.out.println(
                    " .~~[ LYRA ]~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
                    " :                                            :\n" +
                    " :   Malachar e meu pai. Era pra eu te trair  :\n" +
                    " :   em Karn. Mas voce acreditou em mim       :\n" +
                    " :   quando a coisa facil era me entregar.    :\n" +
                    " :   Entao eu estou te contando.              :\n" +
                    " :                                            :\n" +
                    " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
            aguardar(scanner);
            lyraFilhaRevelada = true;
            confiancaLyra += 2;
        } else {
            System.out.println(
                    " .~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
                    " :                                            :\n" +
                    " :   Numa coluna: Emberfall, ano oito. Um     :\n" +
                    " :   nome de mulher e, ao lado: filha         :\n" +
                    " :   localizada, menor, marcada para          :\n" +
                    " :   observacao. Voce denunciou essa filha.   :\n" +
                    " :                                            :\n" +
                    " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
            aguardar(scanner);
        }

        // ESCOLHA 4
        System.out.println();
        System.out.println("   ESCOLHA 4 — A verdade revelada");
        System.out.println("   [1] Isto acaba. Eu vou derrubar a Ordem.");
        System.out.println("   [2] Vinte anos de mentira. Agora essa Chama e minha.");
        System.out.println("   [3] Nao e problema meu. Eu so quero sair daqui.");
        int e4 = lerEscolha(scanner, 3);

        switch (e4) {
            case 1:
                honra += 2;
                verdadeAceita = true;
                cenaJurar(scanner);
                break;
            case 2:
                ambicao += 2;
                verdadeAceita = true;
                pactoVoz = true;
                cenaCobicar(scanner);
                break;
            case 3:
                verdadeNegada = true;
                cenaRecusar(scanner);
                break;
        }

        // A3-13 Malachar
        sprite("npc_malachar");
        System.out.println(
                " .~~[ MALACHAR ]~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
                " :                                            :\n" +
                " :   Voce demorou. Eu nao vou lutar com voce. :\n" +
                " :   Aldric vai te receber de bracos abertos. :\n" +
                " :   E assim que ele coleciona gente.         :\n" +
                " :                                            :\n" +
                " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
        aguardar(scanner);

        System.out.println(
                " .~~[ MALACHAR ]~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
                " :                                            :\n" +
                " :   Eu vou subir com voce. Nao porque confio :\n" +
                " :   em voce. Porque nao tenho mais nada pra  :\n" +
                " :   fazer com o tempo que sobrou.            :\n" +
                " :                                            :\n" +
                " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
        aguardar(scanner);

        malacharAliado = !verdadeNegada;
    }

    static void cenaJurar(Scanner scanner) {
        System.out.println(
                " .~~[ VOCE ]~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
                " :                                            :\n" +
                " :   Eu vou apagar essa Chama. Nao pelo       :\n" +
                " :   reino. Por tres linhas nessa parede.     :\n" +
                " :                                            :\n" +
                " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
        aguardar(scanner);

        System.out.println(
                " .~~[ A VOZ DA CHAMA ]~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
                " :                                            :\n" +
                " :   Que desperdicio. Tudo bem. Eu sei        :\n" +
                " :   esperar. Esperei mil anos, garoto.       :\n" +
                " :                                            :\n" +
                " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
        aguardar(scanner);
    }

    static void cenaCobicar(Scanner scanner) {
        System.out.println(
                " .~~[ VOCE ]~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
                " :                                            :\n" +
                " :   Se o custo ja foi cobrado, alguem devia  :\n" +
                " :   aproveitar o que foi comprado.           :\n" +
                " :                                            :\n" +
                " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
        aguardar(scanner);

        System.out.println(
                " .~~[ A VOZ DA CHAMA ]~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
                " :                                            :\n" +
                " :   Ai esta. Sabia que tinha alguem          :\n" +
                " :   inteligente ai dentro. Suba. Eu te       :\n" +
                " :   espero acesa.                            :\n" +
                " :                                            :\n" +
                " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
        aguardar(scanner);
    }

    static void cenaRecusar(Scanner scanner) {
        System.out.println(
                " .~~[ VOCE ]~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
                " :                                            :\n" +
                " :   Isso e briga de gente velha. Eu nao vou  :\n" +
                " :   carregar guerra de ninguem.              :\n" +
                " :                                            :\n" +
                " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
        aguardar(scanner);

        System.out.println(
                " .~~[ A VOZ DA CHAMA ]~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
                " :                                            :\n" +
                " :   Covardia tambem e uma escolha. E a unica :\n" +
                " :   que ninguem canta em taverna nenhuma.    :\n" +
                " :                                            :\n" +
                " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
        aguardar(scanner);
    }

    // ===================================================================
    //  ATO 4 — A CIDADELA DA AURORA
    // ===================================================================

    static void ato4(Scanner scanner, Personagem jogador) {
        // A4-01 O retorno
        sprite("cenario_cidadela_noite");
        System.out.println(
                " .~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
                " :                                            :\n" +
                " :   A Cidadela e a mesma. Voce e que virou   :\n" +
                " :   outra coisa no caminho. Ao redor, o      :\n" +
                " :   capim e verde. O unico verde que voce    :\n" +
                " :   viu em quatro regioes.                   :\n" +
                " :                                            :\n" +
                " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
        aguardar(scanner);

        // A4-02 Cavaleiro da Ordem
        sprite("mob_cavaleiro_ordem");
        System.out.println(
                " .~~[ CAVALEIRO ]~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
                " :                                            :\n" +
                " :   Larga a espada e sobe sozinho. Por       :\n" +
                " :   favor. Eu nao quero fazer isso.          :\n" +
                " :                                            :\n" +
                " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
        aguardar(scanner);

        Batalha.iniciar(scanner, jogador, Inimigo.cavaleiroDaOrdem());
        Batalha.garantirSobrevivencia(jogador);

        System.out.println(
                " .~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
                " :                                            :\n" +
                " :   Ele cai no primeiro degrau. Voce nao     :\n" +
                " :   olha para baixo enquanto sobe o resto.   :\n" +
                " :                                            :\n" +
                " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
        aguardar(scanner);

        // A4-03 O salao da Chama
        sprite("cenario_salao_chama");
        System.out.println(
                " .~~[ ALDRIC ]~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
                " :                                            :\n" +
                " :   Duskrow, ano dezenove. Voce leu a        :\n" +
                " :   parede. Eu nunca escondi. Eu apenas      :\n" +
                " :   nunca contei, que e diferente.           :\n" +
                " :                                            :\n" +
                " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
        aguardar(scanner);

        System.out.println(
                " .~~[ ALDRIC ]~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
                " :                                            :\n" +
                " :   Antes da Chama morriam nove mil por ano. :\n" +
                " :   Hoje morrem novecentos, escolhidos e     :\n" +
                " :   usados. Eu nao inventei a morte. Eu a    :\n" +
                " :   organizei.                               :\n" +
                " :                                            :\n" +
                " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
        aguardar(scanner);

        // A4-04 A oferta
        sprite("npc_aldric_verdadeiro");
        System.out.println(
                " .~~[ ALDRIC ]~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
                " :                                            :\n" +
                " :   Fica. Aprende. Daqui a cem anos voce     :\n" +
                " :   toma o meu lugar. Nao peco perdao, peco  :\n" +
                " :   continuidade.                            :\n" +
                " :                                            :\n" +
                " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
        aguardar(scanner);

        System.out.println(
                " .~~[ A VOZ DA CHAMA ]~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
                " :                                            :\n" +
                " :   Escolhe. Eu fico com qualquer um dos     :\n" +
                " :   dois. Eu sempre fico.                    :\n" +
                " :                                            :\n" +
                " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
        aguardar(scanner);

        // Lyra chega (aliada ou inimiga)
        if (lyraAliada) {
            System.out.println(
                    " .~~[ LYRA ]~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
                    " :                                            :\n" +
                    " :   Pai, sai da frente. Depois a gente       :\n" +
                    " :   briga. Agora tem um velho de mil anos    :\n" +
                    " :   pra derrubar.                            :\n" +
                    " :                                            :\n" +
                    " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
            aguardar(scanner);
            System.out.println("   Lyra ergue as adagas ao seu lado. Voce nao esta so.");
        } else if (lyraTraida) {
            System.out.println(
                    " .~~[ LYRA ]~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
                    " :                                            :\n" +
                    " :   Eles me pegaram na estrada. Gracas a     :\n" +
                    " :   voce. Eu contei da minha mae. Voce       :\n" +
                    " :   mandou um corvo. Eu aprendi contigo.     :\n" +
                    " :                                            :\n" +
                    " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
            aguardar(scanner);
        }

        // ESCOLHA 5 — o confronto final
        System.out.println();
        System.out.println("   ESCOLHA 5 — O confronto final");
        System.out.println("   [1] Recusar a mao e apagar essa Chama de uma vez");
        System.out.println("   [2] Apertar a mao... e depois tomar tudo");
        System.out.println("   [3] Acabar com isso e ir embora, sem trono nenhum");
        int e5 = lerEscolha(scanner, 3);

        if (e5 == 1 && honra >= 3 && honra > ambicao) {
            finalHeroi(scanner, jogador);
        } else if (e5 == 2 && ambicao >= 3 && ambicao > honra) {
            finalVilao(scanner, jogador);
        } else {
            // Se o jogador pede um final que nao merece, a Voz esfrega o
            // historico na cara dele (A4-07) e cai no final Neutro.
            if ((e5 == 1 && !(honra >= 3 && honra > ambicao)) ||
                (e5 == 2 && !(ambicao >= 3 && ambicao > honra))) {
                cenaSemKarma(scanner);
            }
            finalNeutro(scanner, jogador);
        }
    }

    static void cenaSemKarma(Scanner scanner) {
        System.out.println(
                " .~~[ A VOZ DA CHAMA ]~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
                " :                                            :\n" +
                " :   Agora? Agora voce quer escolher um lado? :\n" +
                " :   Eu vi tudo, garoto. Voce nao tem peso    :\n" +
                " :   pra derrubar nem pra tomar nada. So pra  :\n" +
                " :   ir embora.                               :\n" +
                " :                                            :\n" +
                " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
        aguardar(scanner);
    }

    // ===================================================================
    //  FINAIS
    // ===================================================================

    // Conta quantos aliados lutam ao seu lado no boss.
    static int aliadosNoBoss() {
        int n = 0;
        if (malacharAliado) n++;
        if (lyraAliada) n++;
        return n;
    }

    static void finalHeroi(Scanner scanner, Personagem jogador) {
        sprite("boss_aldric_verdadeiro");
        System.out.println(
                " .~~[ VOCE ]~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
                " :                                            :\n" +
                " :   Eu nao quero continuidade. Eu quero o    :\n" +
                " :   fim.                                     :\n" +
                " :                                            :\n" +
                " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
        aguardar(scanner);

        System.out.println(
                " .~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
                " :                                            :\n" +
                " :   O sorriso permanece enquanto a pele      :\n" +
                " :   racha. O que estava por baixo tem mil    :\n" +
                " :   anos de idade.                           :\n" +
                " :                                            :\n" +
                " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
        aguardar(scanner);

        Batalha.iniciarBoss(scanner, jogador, Inimigo.aldricVerdadeiro(), aliadosNoBoss());
        Batalha.garantirSobrevivencia(jogador);

        sprite("cena_destruicao_chama");
        System.out.println(
                " .~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
                " :                                            :\n" +
                " :   Voce enfia a espada na Chama. Ela grita. :\n" +
                " :   Malachar entra no clarao e segura a      :\n" +
                " :   explosao com o corpo.                    :\n" +
                " :                                            :\n" +
                " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
        aguardar(scanner);

        if (lyraAliada) {
            System.out.println("   Lyra enterra o pai na encosta leste, virado para Emberfall.");
        }
        if (vilaSalva) {
            System.out.println("   Em Greywatch, Brenna acende fogo em quatro chamines. Quatro.");
        }

        System.out.println(
                " .~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
                " :                                            :\n" +
                " :   Vitoria amarga, mas justa.               :\n" +
                " :   FINAL HEROI - A Aurora Renasce.          :\n" +
                " :                                            :\n" +
                " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
        aguardar(scanner);
    }

    static void finalVilao(Scanner scanner, Personagem jogador) {
        sprite("boss_malachar");
        System.out.println(
                " .~~[ VOCE ]~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
                " :                                            :\n" +
                " :   Continuidade, voce disse. Eu prefiro     :\n" +
                " :   sucessao.                                :\n" +
                " :                                            :\n" +
                " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
        aguardar(scanner);

        System.out.println(
                " .~~[ MALACHAR ]~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
                " :                                            :\n" +
                " :   Garoto, eles fizeram voce. Nao vira a    :\n" +
                " :   coisa que te fez. Saia da frente? Nao.   :\n" +
                " :                                            :\n" +
                " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
        aguardar(scanner);

        // Se Lyra foi traida, ela luta contra voce ao lado de Malachar.
        int inimigosExtras = lyraTraida ? 1 : 0;
        Batalha.iniciarBoss(scanner, jogador, Inimigo.malacharBoss(), inimigosExtras);
        Batalha.garantirSobrevivencia(jogador);

        sprite("cena_absorver_chama");
        System.out.println(
                " .~~[ A VOZ DA CHAMA ]~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
                " :                                            :\n" +
                " :   Bem-vindo. Voce vai gostar dos primeiros :\n" +
                " :   cem anos. Os outros novecentos passam    :\n" +
                " :   rapido.                                  :\n" +
                " :                                            :\n" +
                " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
        aguardar(scanner);

        System.out.println(
                " .~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
                " :                                            :\n" +
                " :   Vinte anos depois, um Grao-Mestre de     :\n" +
                " :   rosto jovem poe a mao no ombro de um     :\n" +
                " :   recruta orfao e diz que a culpa da praga :\n" +
                " :   e de um feiticeiro no norte.             :\n" +
                " :                                            :\n" +
                " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
        aguardar(scanner);

        System.out.println(
                " .~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
                " :                                            :\n" +
                " :   O recruta acredita. Eles sempre          :\n" +
                " :   acreditam.                               :\n" +
                " :   FINAL VILAO - O Novo Tirano.             :\n" +
                " :                                            :\n" +
                " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
        aguardar(scanner);
    }

    static void finalNeutro(Scanner scanner, Personagem jogador) {
        sprite("boss_guardiao_chama");
        System.out.println(
                " .~~[ VOCE ]~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
                " :                                            :\n" +
                " :   Eu nao quero o teu trono, Aldric. E nao  :\n" +
                " :   quero salvar ninguem. Eu quero que essa  :\n" +
                " :   coisa apague.                            :\n" +
                " :                                            :\n" +
                " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
        aguardar(scanner);

        System.out.println(
                " .~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
                " :                                            :\n" +
                " :   Do fogo sai uma forma alta e sem rosto:  :\n" +
                " :   o Guardiao, que existe desde o primeiro  :\n" +
                " :   dia e nunca precisou de nome.            :\n" +
                " :                                            :\n" +
                " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
        aguardar(scanner);

        Batalha.iniciarBoss(scanner, jogador, Inimigo.guardiaoDaChama(), aliadosNoBoss());
        Batalha.garantirSobrevivencia(jogador);

        sprite("cena_chama_apagando");
        System.out.println(
                " .~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
                " :                                            :\n" +
                " :   A Chama apaga como vela: devagar, sem    :\n" +
                " :   grito, quase educadamente.               :\n" +
                " :                                            :\n" +
                " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
        aguardar(scanner);

        if (verdadeNegada) {
            System.out.println("   Voce nem contou pra ninguem o que leu naquela parede.");
        }
        if (lyraNoGrupo) {
            System.out.println("   Lyra te acompanha ate a bifurcacao da estrada. Depois pega o outro lado.");
        }

        System.out.println(
                " .~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
                " :                                            :\n" +
                " :   Nao ha cancao sobre voce. Nao ha         :\n" +
                " :   estatua. Voce atravessa a fronteira      :\n" +
                " :   norte numa manha fria, sem missao, sem   :\n" +
                " :   Ordem e sem fardo.                       :\n" +
                " :                                            :\n" +
                " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
        aguardar(scanner);

        System.out.println(
                " .~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
                " :                                            :\n" +
                " :   Final melancolico, de liberdade sem      :\n" +
                " :   gloria.                                  :\n" +
                " :   FINAL NEUTRO - O Andarilho.              :\n" +
                " :                                            :\n" +
                " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
        aguardar(scanner);
    }
}
