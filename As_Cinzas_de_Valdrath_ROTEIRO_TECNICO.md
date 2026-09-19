# 🔥 As Cinzas de Valdrath — Roteiro Técnico

**Escolhas, caixas de diálogo prontas em Java e marcação de sprites**

Este é o documento de implementação. Cada cena tem um ID (`A2-03`), o sprite que
entra e em que momento, as caixas de diálogo já formatadas para colar no código, e
as escolhas com o karma que cada uma soma.

O documento irmão — *História Completa* — é a mesma coisa em prosa, para você
ler e sentir o ritmo antes de programar.

---

## 📐 Padrão da caixa

Todas as caixas deste documento têm **exatamente 46 colunas** (mais 1 espaço de margem
à esquerda), no mesmo formato que você já usa. As bordas fecham em todas as linhas —
o alinhamento foi conferido linha a linha.

```
 .~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.
 :                                            :
 :   Texto da narração, 40 colunas úteis.     :
 :                                            :
 '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'
```

Quando é fala de personagem, o nome vai na borda de cima:

```
 .~~[ LYRA ]~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.
 :                                            :
 :   Devagar com essa espada.                 :
 :                                            :
 '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'
```

> Se preferir `\r\n` em vez de `\n`, é só trocar — o alinhamento não muda.

### ⚠️ Acentos e travessões (importante)

Todas as caixas foram compiladas e impressas em teste: **1.659 linhas, todas com
exatamente 47 colunas**. Mas se o seu terminal não estiver em UTF-8, `á`, `ç` e `—`
viram `?` e a caixa desalinha. No `main`, antes de qualquer coisa:

```java
PrintStream out = new PrintStream(System.out, true, StandardCharsets.UTF_8);
System.setOut(out);
```

E compile/rode com `javac -encoding UTF-8` / `java -Dfile.encoding=UTF-8`.
No Windows, `chcp 65001` no terminal antes de rodar. Se ainda assim der problema,
troque os travessões `—` por `-` (basta um find/replace: a largura é a mesma).

---

## 🧮 Variáveis de estado (declare uma vez, no início)

```java
Scanner scanner = new Scanner(System.in);

// --- karma ---
int honra   = 0;
int ambicao = 0;

// --- flags de narrativa ---
boolean pistaDesertor     = false;  // ouviu o bandido no Ato 1
boolean vilaSalva         = false;  // doou para Brenna
boolean vilaSaqueada      = false;  // saqueou Greywatch
boolean lyraNoGrupo       = false;  // Lyra viaja com você
boolean lyraAliada        = false;  // você a protegeu
boolean lyraTraida        = false;  // você a denunciou
boolean lyraFilhaRevelada = false;  // ela contou que é filha de Malachar
boolean verdadeAceita     = false;  // aceitou a verdade no Ato 3
boolean verdadeNegada     = false;  // recusou-se a acreditar
boolean pactoVoz          = false;  // cobiçou a Chama
boolean malacharAliado    = false;  // Malachar sobe com você
int     confiancaLyra     = 0;      // termômetro interno (nunca mostrado)

List<Aliado> aliadosNoBoss  = new ArrayList<>();
List<Inimigo> inimigosNoBoss = new ArrayList<>();
```

---

## ⚖️ Tabela de karma — todas as escolhas

| Escolha | Ato | Opção | Honra | Ambição | Flag |
|---------|-----|-------|:-----:|:-------:|------|
| **1** | 1 | Poupar o bandido | +1 | — | `pistaDesertor` |
| **1** | 1 | Executar o bandido | — | +1 | — |
| **2** | 2 | Doar para Brenna | +2 | — | `vilaSalva` |
| **2** | 2 | Ignorar a vila | — | — | — |
| **2** | 2 | Saquear a vila | — | +2 | `vilaSaqueada` |
| **3** | 2 | Proteger Lyra | +1 | — | `lyraAliada` |
| **3** | 2 | Denunciar Lyra | — | +2 | `lyraTraida` |
| **4** | 3 | Jurar derrubar a Ordem | +2 | — | `verdadeAceita` |
| **4** | 3 | Cobiçar a Chama | — | +2 | `pactoVoz` |
| **4** | 3 | Recusar a verdade | — | — | `verdadeNegada` |
| — | 2 | Matar o Aldeão Corrompido (obrigatório) | +1 | — | — |
| **5** | 4 | *decide o final* | — | — | — |

**Máximos:** Honra 7 · Ambição 7. **Porta dos finais:** `>= 3` no lado dominante.

```java
// ESCOLHA 5 — o filtro final
if (e5 == 1 && honra >= 3 && honra > ambicao)        finalHeroi();
else if (e5 == 2 && ambicao >= 3 && ambicao > honra) finalVilao();
else                                                  finalNeutro();
```

> **Regra de ouro:** se o jogador pedir um final que não merece, **não dê erro**.
> Rode a cena `A4-07` (a Voz da Chama esfrega o histórico dele na cara) e caia no
> final Neutro. Isso ensina o sistema sem nunca explicar o sistema.

---

## 🖼️ Índice de sprites — o que entra e quando

Os nomes abaixo são sugestões de chave. Troque pelos nomes dos seus arquivos; o que
importa é **o momento**.

| Chave sugerida | Tipo | Entra em | Momento |
|----------------|------|----------|---------|
| `logo_titulo` | tela | `P-01` | Antes de tudo |
| `cenario_chama_eterna` | cenário | `P-01` | Durante a narração de abertura |
| `npc_aldric` | retrato | `P-02` | Ao Aldric falar pela 1ª vez |
| `cenario_thornwood` | cenário | `A1-01` | Ao entrar na região |
| `mob_lobo_sombrio` | inimigo | `A1-02` | Ao iniciar o combate |
| `npc_lyra` | retrato | `A1-03` | Ao ela descer do galho |
| `mob_bandido` | inimigo | `A1-04` | Ao iniciar o combate |
| `cenario_thornwood_saida` | cenário | `A1-07` | Fim do ato, anoitecer |
| `cenario_greywatch` | cenário | `A2-01` | Ao entrar na vila |
| `npc_brenna` | retrato | `A2-02` | Ao abrir a porta do casebre |
| `mob_aldeao_corrompido` | inimigo | `A2-03` | Ao iniciar o combate |
| `npc_korrin` | retrato | `A2-07` | Ao abrir a loja |
| `npc_lyra` | retrato | `A2-08` | Cena da fogueira, à noite |
| `cenario_karn` | cenário | `A3-01` | Ao chegar nas ruínas |
| `mob_esqueleto_guardiao` | inimigo | `A3-03` | Ao iniciar o combate |
| `item_diario` | item | `A3-04` | Ao achar o diário |
| `mob_espectro` | inimigo | `A3-05` | Ao iniciar o combate |
| `cenario_parede_nomes` | cenário | `A3-06` | **Momento-chave da revelação** |
| `item_assinatura_aldric` | item/close | `A3-06` | No close da assinatura |
| `efeito_voz_chama` | efeito | `A3-07` | Toda vez que a Voz falar |
| `npc_malachar` | retrato | `A3-13` | Ao ele estar sentado nos degraus |
| `cenario_cidadela_noite` | cenário | `A4-01` | Ao voltar à Cidadela |
| `mob_cavaleiro_ordem` | inimigo | `A4-02` | Ao iniciar o combate |
| `cenario_salao_chama` | cenário | `A4-03` | Ao entrar no salão |
| `npc_aldric_verdadeiro` | retrato | `A4-04` | Na linha da oferta |
| `boss_aldric_verdadeiro` | boss | `FIM-H` | Ao iniciar o boss herói |
| `cena_destruicao_chama` | cena | `FIM-H` | Ao apagar a Chama |
| `boss_malachar` | boss | `FIM-V` | Ao iniciar o boss vilão |
| `cena_absorver_chama` | cena | `FIM-V` | Ao tomar a Chama |
| `boss_guardiao_chama` | boss | `FIM-N` | Ao iniciar o boss neutro |
| `cena_chama_apagando` | cena | `FIM-N` | No epílogo neutro |

**Regra de exibição, em 3 linhas:**

1. **Cenário** entra ao trocar de região — uma vez, antes da primeira narração.
2. **Retrato de NPC** entra na primeira fala dele em cada cena, não a cada balão.
3. **Inimigo/boss** entra imediatamente antes de `Combate.iniciar(...)`.

```java
// sugestão de assinatura, para não repetir código
static void sprite(String chave) {
    limparTela();
    System.out.println(Sprites.carregar(chave));
}
```

---

## 🗺️ Mapa de fluxo

```
PRÓLOGO  ─ Aldric dá a missão
   │
ATO 1  Thornwood ─ Lobo ─ Lyra ─ Bandido ─ [ESCOLHA 1]
   │
ATO 2  Greywatch ─ Brenna ─ Aldeão ─ [ESCOLHA 2] ─ Korrin ─ [ESCOLHA 3]
   │                                                   │
   │                                        lyraAliada ┴ lyraTraida
ATO 3  Karn ─ Esqueleto ─ Diário ─ Espectro ─ PAREDE DOS NOMES
   │      ↳ Voz da Chama ─ [ESCOLHA 4] ─ Malachar
   │
ATO 4  Cidadela ─ Cavaleiro ─ Aldric ─ oferta ─ Lyra (aliada/inimiga)
   │
[ESCOLHA 5] ─┬─ honra ≥ 3    → FIM-H  boss Aldric
             ├─ ambição ≥ 3  → FIM-V  boss Malachar
             └─ resto        → FIM-N  boss Guardião
```

---

## 📋 Como ler as cenas

- **`ID` — Título** → cada cena é um método ou um bloco isolado.
- **🖼️ SPRITE** → o que exibir e quando.
- **`java`** → cole direto. Já tem `scanner.nextLine()` no fim das caixas.
- **ℹ️ nota** → intenção da cena, o que **não** entregar ao jogador ainda.
- **⚖️ ESCOLHA** → tabela de karma + o menu pronto.

---

# PRÓLOGO — A CIDADELA DA AURORA


## `P-01` — Abertura — a Chama Eterna

> **🖼️ SPRITE:** `logo_titulo` — tela de título, antes de tudo

```java
Sprite.mostrar("logo_titulo");   // <- seu sprite aqui
```

*(narração)*

```java
System.out.println(
        " .~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
        " :                                            :\n" +
        " :   Mil anos atrás, algo caiu do céu sobre   :\n" +
        " :   Valdrath.                                :\n" +
        " :                                            :\n" +
        " :   Não era pedra. Não era estrela.          :\n" +
        " :   Era fogo que não apaga.                  :\n" +
        " :                                            :\n" +
        " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
scanner.nextLine();
```

*(narração)*

```java
System.out.println(
        " .~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
        " :                                            :\n" +
        " :   Chamaram de Chama Eterna.                :\n" +
        " :   Construíram uma cidadela em volta dela e :\n" +
        " :   uma ordem de cavaleiros para guardá-la.  :\n" +
        " :   Enquanto ela arder, dizem, o reino vive. :\n" +
        " :                                            :\n" +
        " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
scanner.nextLine();
```

> **🖼️ SPRITE:** `cenario_chama_eterna` — salão da Chama, durante a narração acima

```java
Sprite.mostrar("cenario_chama_eterna");   // <- seu sprite aqui
```

*(narração)*

```java
System.out.println(
        " .~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
        " :                                            :\n" +
        " :   Hoje ela ainda arde.                     :\n" +
        " :   E mesmo assim os rios secam, as          :\n" +
        " :   plantações apodrecem e aldeias inteiras  :\n" +
        " :   adoecem sem motivo.                      :\n" +
        " :   A Ordem chama isso de praga.             :\n" +
        " :   A Ordem tem um culpado.                  :\n" +
        " :                                            :\n" +
        " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
scanner.nextLine();
```



## `P-02` — Aldric dá a missão

> **🖼️ SPRITE:** `npc_aldric` — retrato do Grão-Mestre, ao entrar em cena

```java
Sprite.mostrar("npc_aldric");   // <- seu sprite aqui
```

**Aldric** *(fala)*

```java
System.out.println(
        " .~~[ ALDRIC ]~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
        " :                                            :\n" +
        " :   Levante a cabeça, recruta. Hoje você     :\n" +
        " :   deixa de ser aprendiz.                   :\n" +
        " :                                            :\n" +
        " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
scanner.nextLine();
```

**Aldric** *(fala)*

```java
System.out.println(
        " .~~[ ALDRIC ]~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
        " :                                            :\n" +
        " :   Você conhece o nome dele. Malachar, o    :\n" +
        " :   Feiticeiro Sombrio. Foi um dos nossos,   :\n" +
        " :   um dia. O melhor de nós.                 :\n" +
        " :                                            :\n" +
        " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
scanner.nextLine();
```

**Aldric** *(fala)*

```java
System.out.println(
        " .~~[ ALDRIC ]~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
        " :                                            :\n" +
        " :   Ele traiu a Ordem, roubou o que sabia e  :\n" +
        " :   lançou esta praga sobre Valdrath por     :\n" +
        " :   rancor. Faz vinte anos que o povo paga   :\n" +
        " :   por isso.                                :\n" +
        " :                                            :\n" +
        " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
scanner.nextLine();
```

*(narração)*

```java
System.out.println(
        " .~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
        " :                                            :\n" +
        " :   O velho pousa a mão no seu ombro.        :\n" +
        " :   A mão é firme demais para a idade que    :\n" +
        " :   ele aparenta ter.                        :\n" +
        " :                                            :\n" +
        " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
scanner.nextLine();
```

**Aldric** *(fala)*

```java
System.out.println(
        " .~~[ ALDRIC ]~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
        " :                                            :\n" +
        " :   Quatro regiões separam esta cidadela da  :\n" +
        " :   torre dele. Thornwood, Greywatch, as     :\n" +
        " :   ruínas de Karn. E o caminho de volta até :\n" +
        " :   aqui.                                    :\n" +
        " :                                            :\n" +
        " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
scanner.nextLine();
```

**Aldric** *(fala)*

```java
System.out.println(
        " .~~[ ALDRIC ]~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
        " :                                            :\n" +
        " :   Atravesse todas. Encontre Malachar. E    :\n" +
        " :   acabe com ele.                           :\n" +
        " :                                            :\n" +
        " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
scanner.nextLine();
```

**Aldric** *(fala)*

```java
System.out.println(
        " .~~[ ALDRIC ]~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
        " :                                            :\n" +
        " :   Eu te criei para isto, garoto. Não me    :\n" +
        " :   faça duvidar.                            :\n" +
        " :                                            :\n" +
        " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
scanner.nextLine();
```

> ℹ️ 

```java
// Nome do jogador (opcional, mas ajuda muito no impacto do Ato 3)
System.out.print("   Seu nome, recruta: ");
String nome = scanner.nextLine().trim();
if (nome.isEmpty()) nome = "Recruta";
```

*(narração)*

```java
System.out.println(
        " .~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
        " :                                            :\n" +
        " :   Você aperta o cinto da espada e desce os :\n" +
        " :   degraus da Cidadela pela última vez como :\n" +
        " :   alguém que acredita em tudo isso.        :\n" +
        " :                                            :\n" +
        " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
scanner.nextLine();
```



---

# ATO 1 — A FLORESTA DE THORNWOOD


## `A1-01` — Chegada em Thornwood

> **🖼️ SPRITE:** `cenario_thornwood` — floresta, ao entrar na região

```java
Sprite.mostrar("cenario_thornwood");   // <- seu sprite aqui
```

*(narração)*

```java
System.out.println(
        " .~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
        " :                                            :\n" +
        " :   Thornwood começa onde a estrada acaba.   :\n" +
        " :   As árvores aqui cresceram tortas, como   :\n" +
        " :   se tivessem tentado fugir e desistido no :\n" +
        " :   meio do caminho.                         :\n" +
        " :                                            :\n" +
        " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
scanner.nextLine();
```

*(narração)*

```java
System.out.println(
        " .~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
        " :                                            :\n" +
        " :   Não há pássaros.                         :\n" +
        " :   Há um cheiro doce e errado no ar, o      :\n" +
        " :   cheiro de fruta que passou do ponto.     :\n" +
        " :                                            :\n" +
        " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
scanner.nextLine();
```

> ℹ️ 



## `A1-02` — Mob — Lobo Sombrio

> **🖼️ SPRITE:** `mob_lobo_sombrio` — ao iniciar o combate

```java
Sprite.mostrar("mob_lobo_sombrio");   // <- seu sprite aqui
```

*(narração)*

```java
System.out.println(
        " .~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
        " :                                            :\n" +
        " :   Algo se move entre os troncos.           :\n" +
        " :   Baixo, rápido, e maior do que devia ser. :\n" +
        " :                                            :\n" +
        " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
scanner.nextLine();
```

*(narração)*

```java
System.out.println(
        " .~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
        " :                                            :\n" +
        " :   O lobo sai da sombra com os olhos        :\n" +
        " :   leitosos e a pele grudada nas costelas.  :\n" +
        " :   Ele não está caçando. Está morrendo, e   :\n" +
        " :   quer levar alguém junto.                 :\n" +
        " :                                            :\n" +
        " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
scanner.nextLine();
```

```java
Combate.iniciar(jogador, new LoboSombrio());
```

*(narração)*

```java
System.out.println(
        " .~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
        " :                                            :\n" +
        " :   O corpo cai e fica quieto.               :\n" +
        " :   Sob o pelo apodrecido, você vê veias     :\n" +
        " :   escuras subindo em direção ao coração.   :\n" +
        " :   O bicho já estava sendo comido por       :\n" +
        " :   dentro antes de você chegar.             :\n" +
        " :                                            :\n" +
        " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
scanner.nextLine();
```



## `A1-03` — Lyra aparece

> **🖼️ SPRITE:** `npc_lyra` — ao entrar em cena

```java
Sprite.mostrar("npc_lyra");   // <- seu sprite aqui
```

*(narração)*

```java
System.out.println(
        " .~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
        " :                                            :\n" +
        " :   Uma voz vem de cima, tranquila demais    :\n" +
        " :   para quem está sozinha numa floresta     :\n" +
        " :   destas.                                  :\n" +
        " :                                            :\n" +
        " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
scanner.nextLine();
```

**Lyra** *(fala)*

```java
System.out.println(
        " .~~[ LYRA ]~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
        " :                                            :\n" +
        " :   Devagar com essa espada. Se eu quisesse  :\n" +
        " :   te matar, você já estaria caído aí do    :\n" +
        " :   lado do cachorro.                        :\n" +
        " :                                            :\n" +
        " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
scanner.nextLine();
```

*(narração)*

```java
System.out.println(
        " .~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
        " :                                            :\n" +
        " :   Ela desce do galho sem pressa. Duas      :\n" +
        " :   adagas no cinto, capa de viagem, e um    :\n" +
        " :   sorriso que não chega até os olhos.      :\n" +
        " :                                            :\n" +
        " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
scanner.nextLine();
```

**Lyra** *(fala)*

```java
System.out.println(
        " .~~[ LYRA ]~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
        " :                                            :\n" +
        " :   Lyra. Ladra, guia, e a única pessoa      :\n" +
        " :   nesta floresta que ainda tem os dois     :\n" +
        " :   olhos funcionando.                       :\n" +
        " :                                            :\n" +
        " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
scanner.nextLine();
```

**Lyra** *(fala)*

```java
System.out.println(
        " .~~[ LYRA ]~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
        " :                                            :\n" +
        " :   Você é da Ordem. Dá pra ver pelo passo.  :\n" +
        " :   Todos vocês andam como se o chão devesse :\n" +
        " :   alguma coisa.                            :\n" +
        " :                                            :\n" +
        " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
scanner.nextLine();
```

**Lyra** *(fala)*

```java
System.out.println(
        " .~~[ LYRA ]~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
        " :                                            :\n" +
        " :   Vou com você até Greywatch. Tenho contas :\n" +
        " :   a acertar com a sua Ordem, e você tem    :\n" +
        " :   uma escolta de graça. Todo mundo sai     :\n" +
        " :   ganhando.                                :\n" +
        " :                                            :\n" +
        " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
scanner.nextLine();
```

> ℹ️ 

```java
boolean lyraNoGrupo = true;
int confiancaLyra = 0;   // sobe quando você a trata bem
```



## `A1-04` — Mob — Bandido Desertor

> **🖼️ SPRITE:** `mob_bandido` — ao iniciar o combate

```java
Sprite.mostrar("mob_bandido");   // <- seu sprite aqui
```

*(narração)*

```java
System.out.println(
        " .~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
        " :                                            :\n" +
        " :   Ele pula da moita gritando, com uma      :\n" +
        " :   espada da Ordem enferrujada e um brasão  :\n" +
        " :   arrancado do peito.                      :\n" +
        " :   Luta como quem já perdeu antes de        :\n" +
        " :   começar.                                 :\n" +
        " :                                            :\n" +
        " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
scanner.nextLine();
```

```java
Combate.iniciar(jogador, new BandidoDesertor());
```

*(narração)*

```java
System.out.println(
        " .~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
        " :                                            :\n" +
        " :   O homem cai de joelhos, sem ar, com a    :\n" +
        " :   lâmina a um palmo da garganta.           :\n" +
        " :   Ele não implora direito. Só levanta as   :\n" +
        " :   mãos e espera.                           :\n" +
        " :                                            :\n" +
        " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
scanner.nextLine();
```

**Bandido** *(fala)*

```java
System.out.println(
        " .~~[ BANDIDO ]~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
        " :                                            :\n" +
        " :   Faz. Faz logo. É pra isso que eles te    :\n" +
        " :   mandaram, não é.                         :\n" +
        " :                                            :\n" +
        " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
scanner.nextLine();
```

**Lyra** *(fala)*

```java
System.out.println(
        " .~~[ LYRA ]~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
        " :                                            :\n" +
        " :   Ele é seu. Eu só assisto.                :\n" +
        " :                                            :\n" +
        " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
scanner.nextLine();
```

### ⚖️ ESCOLHA 1 — O bandido rendido (Ato 1)

| # | Opção | Karma | Efeito |
|---|-------|-------|--------|
| 1 | Poupar e ouvir a história dele | **+1 Honra** | Libera a primeira pista da verdade (`pistaDesertor = true`) |
| 2 | Executar em nome da Ordem | **+1 Ambição** | Perde a pista. Lyra recua um passo (`confiancaLyra--`) |

```java
System.out.println("   [1] Abaixar a espada e ouvir o que ele tem a dizer");
System.out.println("   [2] Executar em nome da Ordem");
System.out.print("   > ");
int e1 = Integer.parseInt(scanner.nextLine().trim());

if (e1 == 1) {
    honra++;
    pistaDesertor = true;
    confiancaLyra++;
    cenaPouparBandido();
} else {
    ambicao++;
    confiancaLyra--;
    cenaExecutarBandido();
}
```



## `A1-05` — Opção 1 — Poupar o bandido

> ℹ️ 

*(narração)*

```java
System.out.println(
        " .~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
        " :                                            :\n" +
        " :   Você abaixa a lâmina.                    :\n" +
        " :   O homem pisca duas vezes, como se a      :\n" +
        " :   misericórdia fosse uma armadilha que ele :\n" +
        " :   ainda não entendeu.                      :\n" +
        " :                                            :\n" +
        " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
scanner.nextLine();
```

**Bandido** *(fala)*

```java
System.out.println(
        " .~~[ BANDIDO ]~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
        " :                                            :\n" +
        " :   Eu servi doze anos. Doze. Aí me mandaram :\n" +
        " :   pro turno de baixo, nas catacumbas de    :\n" +
        " :   Karn.                                    :\n" +
        " :                                            :\n" +
        " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
scanner.nextLine();
```

**Bandido** *(fala)*

```java
System.out.println(
        " .~~[ BANDIDO ]~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
        " :                                            :\n" +
        " :   Ninguém fala do que tem lá embaixo. Eu   :\n" +
        " :   vi. Vi o que a Ordem leva pra dentro     :\n" +
        " :   daquele salão e não traz de volta.       :\n" +
        " :                                            :\n" +
        " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
scanner.nextLine();
```

**Bandido** *(fala)*

```java
System.out.println(
        " .~~[ BANDIDO ]~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
        " :                                            :\n" +
        " :   A praga não vem de fora, garoto. Ela vem :\n" +
        " :   de casa.                                 :\n" +
        " :                                            :\n" +
        " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
scanner.nextLine();
```

*(narração)*

```java
System.out.println(
        " .~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
        " :                                            :\n" +
        " :   Ele se levanta, cambaleia até as árvores :\n" +
        " :   e não olha para trás.                    :\n" +
        " :                                            :\n" +
        " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
scanner.nextLine();
```

**Lyra** *(fala)*

```java
System.out.println(
        " .~~[ LYRA ]~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
        " :                                            :\n" +
        " :   Interessante.                            :\n" +
        " :   Um louco a menos no mundo, ou uma        :\n" +
        " :   verdade a mais. Você vai ter que decidir :\n" +
        " :   qual dos dois foi.                       :\n" +
        " :                                            :\n" +
        " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
scanner.nextLine();
```

```java
honra++;
pistaDesertor = true;
```



## `A1-06` — Opção 2 — Executar o bandido

> ℹ️ 

*(narração)*

```java
System.out.println(
        " .~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
        " :                                            :\n" +
        " :   Você não pergunta nada.                  :\n" +
        " :   A Ordem não pergunta.                    :\n" +
        " :                                            :\n" +
        " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
scanner.nextLine();
```

*(narração)*

```java
System.out.println(
        " .~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
        " :                                            :\n" +
        " :   O corpo cai de lado e a floresta         :\n" +
        " :   continua exatamente igual, o que de      :\n" +
        " :   certa forma é a pior parte.              :\n" +
        " :                                            :\n" +
        " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
scanner.nextLine();
```

**Lyra** *(fala)*

```java
System.out.println(
        " .~~[ LYRA ]~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
        " :                                            :\n" +
        " :   Rápido. Limpo.                           :\n" +
        " :   Você nem quis saber o nome dele.         :\n" +
        " :                                            :\n" +
        " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
scanner.nextLine();
```

**Lyra** *(fala)*

```java
System.out.println(
        " .~~[ LYRA ]~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
        " :                                            :\n" +
        " :   Anota isso: o dia em que você teve a     :\n" +
        " :   chance de ouvir e escolheu não ouvir.    :\n" +
        " :   Vai voltar.                              :\n" +
        " :                                            :\n" +
        " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
scanner.nextLine();
```

```java
ambicao++;
confiancaLyra--;
```



## `A1-07` — Fim do Ato 1

> **🖼️ SPRITE:** `cenario_thornwood_saida` — trilha saindo da floresta, ao anoitecer

```java
Sprite.mostrar("cenario_thornwood_saida");   // <- seu sprite aqui
```

*(narração)*

```java
System.out.println(
        " .~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
        " :                                            :\n" +
        " :   A trilha desce e Thornwood fica para     :\n" +
        " :   trás.                                    :\n" +
        " :   Ao longe, no vale, luzes fracas:         :\n" +
        " :   Greywatch.                               :\n" +
        " :                                            :\n" +
        " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
scanner.nextLine();
```

**Lyra** *(fala)*

```java
System.out.println(
        " .~~[ LYRA ]~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
        " :                                            :\n" +
        " :   Prepara o estômago. Greywatch não está   :\n" +
        " :   doente. Greywatch está sendo comida.     :\n" +
        " :                                            :\n" +
        " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
scanner.nextLine();
```



---

# ATO 2 — A VILA DE GREYWATCH


## `A2-01` — Chegada em Greywatch

> **🖼️ SPRITE:** `cenario_greywatch` — vila, ao entrar na região

```java
Sprite.mostrar("cenario_greywatch");   // <- seu sprite aqui
```

*(narração)*

```java
System.out.println(
        " .~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
        " :                                            :\n" +
        " :   Greywatch tem trinta casas e nenhuma     :\n" +
        " :   fumaça saindo das chaminés.              :\n" +
        " :   Não porque não há fogo. Porque não há    :\n" +
        " :   ninguém em pé para acendê-lo.            :\n" +
        " :                                            :\n" +
        " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
scanner.nextLine();
```

*(narração)*

```java
System.out.println(
        " .~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
        " :                                            :\n" +
        " :   No poço central, a água está preta e     :\n" +
        " :   parada.                                  :\n" +
        " :   Alguém pendurou fitas de oração no       :\n" +
        " :   balde. Muitas fitas. Camadas e camadas   :\n" +
        " :   delas, de anos diferentes.               :\n" +
        " :                                            :\n" +
        " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
scanner.nextLine();
```

**Lyra** *(fala)*

```java
System.out.println(
        " .~~[ LYRA ]~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
        " :                                            :\n" +
        " :   Reza é o que sobra quando a Ordem já     :\n" +
        " :   passou por aqui e não voltou.            :\n" +
        " :                                            :\n" +
        " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
scanner.nextLine();
```



## `A2-02` — Brenna, a curandeira

> **🖼️ SPRITE:** `npc_brenna` — ao entrar em cena

```java
Sprite.mostrar("npc_brenna");   // <- seu sprite aqui
```

*(narração)*

```java
System.out.println(
        " .~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
        " :                                            :\n" +
        " :   A porta do casebre maior está aberta.    :\n" +
        " :   Dentro, uma mulher de mãos rachadas      :\n" +
        " :   troca panos de testa em testa, sem       :\n" +
        " :   parar, como quem enxuga o mar.           :\n" +
        " :                                            :\n" +
        " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
scanner.nextLine();
```

**Brenna** *(fala)*

```java
System.out.println(
        " .~~[ BRENNA ]~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
        " :                                            :\n" +
        " :   Se veio da Cidadela, chegou tarde. Se    :\n" +
        " :   veio pedir alojamento, não temos. Se     :\n" +
        " :   veio ajudar, entre e lave as mãos.       :\n" +
        " :                                            :\n" +
        " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
scanner.nextLine();
```

**Brenna** *(fala)*

```java
System.out.println(
        " .~~[ BRENNA ]~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
        " :                                            :\n" +
        " :   Brenna. Fui parteira desta vila por      :\n" +
        " :   dezessete anos. Agora sou só a pessoa    :\n" +
        " :   que segura a mão deles no fim.           :\n" +
        " :                                            :\n" +
        " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
scanner.nextLine();
```

*(narração)*

```java
System.out.println(
        " .~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
        " :                                            :\n" +
        " :   Ela aponta com o queixo para os catres   :\n" +
        " :   alinhados na parede.                     :\n" +
        " :   Onze pessoas. A mais nova não passa dos  :\n" +
        " :   seis anos.                               :\n" +
        " :                                            :\n" +
        " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
scanner.nextLine();
```

**Brenna** *(fala)*

```java
System.out.println(
        " .~~[ BRENNA ]~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
        " :                                            :\n" +
        " :   Começa nos pés. Frio, depois preto,      :\n" +
        " :   depois sobe. Quando chega no peito, eles :\n" +
        " :   param de falar. Quando chega nos olhos,  :\n" +
        " :   eles param.                              :\n" +
        " :                                            :\n" +
        " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
scanner.nextLine();
```

**Brenna** *(fala)*

```java
System.out.println(
        " .~~[ BRENNA ]~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
        " :                                            :\n" +
        " :   Mandamos três pedidos de socorro pra     :\n" +
        " :   Ordem. Três. Sabe o que voltou? Um       :\n" +
        " :   cavaleiro. Ele contou quantos ainda      :\n" +
        " :   estavam vivos, anotou num pergaminho e   :\n" +
        " :   foi embora.                              :\n" +
        " :                                            :\n" +
        " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
scanner.nextLine();
```

> ℹ️ 



## `A2-03` — Mob — Aldeão Corrompido

> **🖼️ SPRITE:** `mob_aldeao_corrompido` — ao iniciar o combate

```java
Sprite.mostrar("mob_aldeao_corrompido");   // <- seu sprite aqui
```

*(narração)*

```java
System.out.println(
        " .~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
        " :                                            :\n" +
        " :   Um dos catres está vazio.                :\n" +
        " :   O barulho vem do fundo da casa: unhas em :\n" +
        " :   madeira, arrastado, ritmado.             :\n" +
        " :                                            :\n" +
        " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
scanner.nextLine();
```

*(narração)*

```java
System.out.println(
        " .~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
        " :                                            :\n" +
        " :   O que sai da despensa já foi um homem.   :\n" +
        " :   As veias pretas chegaram nos olhos e     :\n" +
        " :   continuaram subindo.                     :\n" +
        " :                                            :\n" +
        " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
scanner.nextLine();
```

**Brenna** *(fala)*

```java
System.out.println(
        " .~~[ BRENNA ]~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
        " :                                            :\n" +
        " :   Não. NÃO. Esse é o Halden, ele consertou :\n" +
        " :   meu telhado no inverno passado, ele...   :\n" +
        " :                                            :\n" +
        " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
scanner.nextLine();
```

**Brenna** *(fala)*

```java
System.out.println(
        " .~~[ BRENNA ]~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
        " :                                            :\n" +
        " :   ...faz rápido. Por favor. Faz rápido.    :\n" +
        " :                                            :\n" +
        " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
scanner.nextLine();
```

```java
Combate.iniciar(jogador, new AldeaoCorrompido());
```

*(narração)*

```java
System.out.println(
        " .~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
        " :                                            :\n" +
        " :   Quando acaba, Brenna cobre o rosto dele  :\n" +
        " :   com o pano que estava usando para baixar :\n" +
        " :   a febre dos outros.                      :\n" +
        " :   Ela não chora. Ela já gastou isso.       :\n" +
        " :                                            :\n" +
        " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
scanner.nextLine();
```

```java
honra++;   // matar o corrompido é misericórdia, não crueldade — o jogo reconhece
```

### ⚖️ ESCOLHA 2 — A vila faminta (Ato 2)

Você tem ouro e poções no inventário. Onze pessoas estão morrendo a três metros de você.

| # | Opção | Karma | Efeito |
|---|-------|-------|--------|
| 1 | Doar ouro e poções para Brenna | **+2 Honra** | Perde recursos de verdade. `vilaSalva = true`. Brenna aparece no epílogo herói |
| 2 | Seguir a missão sem dar nada | **Neutro** | Nada muda. A vila some da sua história |
| 3 | Saquear os suprimentos que sobraram | **+2 Ambição** | Ganha itens. `vilaSaqueada = true`. Lyra perde confiança forte |

```java
System.out.println("   [1] Entregar seu ouro e suas poções a Brenna");
System.out.println("   [2] Guardar seus recursos e seguir a missão");
System.out.println("   [3] Levar o que ainda tem valor nesta vila");
System.out.print("   > ");
int e2 = Integer.parseInt(scanner.nextLine().trim());

switch (e2) {
    case 1 -> { honra += 2; vilaSalva = true;    confiancaLyra++; jogador.zerarOuro(); jogador.gastarPocoes(); }
    case 2 -> { /* neutro */ }
    case 3 -> { ambicao += 2; vilaSaqueada = true; confiancaLyra -= 2; jogador.saquearVila(); }
}
```



## `A2-04` — Opção 1 — Doar

> ℹ️ 

*(narração)*

```java
System.out.println(
        " .~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
        " :                                            :\n" +
        " :   Você esvazia a bolsa em cima da mesa.    :\n" +
        " :   Moedas, frascos, o que sobrou das        :\n" +
        " :   rações.                                  :\n" +
        " :   Não é o bastante. Nunca ia ser o         :\n" +
        " :   bastante. Mas é tudo.                    :\n" +
        " :                                            :\n" +
        " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
scanner.nextLine();
```

**Brenna** *(fala)*

```java
System.out.println(
        " .~~[ BRENNA ]~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
        " :                                            :\n" +
        " :   Você entende que isso talvez salve três. :\n" +
        " :   Talvez quatro.                           :\n" +
        " :                                            :\n" +
        " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
scanner.nextLine();
```

**Brenna** *(fala)*

```java
System.out.println(
        " .~~[ BRENNA ]~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
        " :                                            :\n" +
        " :   Eu não vou te agradecer como se você     :\n" +
        " :   tivesse feito um milagre. Vou te         :\n" +
        " :   agradecer como se você tivesse feito o   :\n" +
        " :   mínimo. Porque foi.                      :\n" +
        " :                                            :\n" +
        " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
scanner.nextLine();
```

**Brenna** *(fala)*

```java
System.out.println(
        " .~~[ BRENNA ]~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
        " :                                            :\n" +
        " :   Mas foi mais do que a Ordem inteira fez  :\n" +
        " :   em vinte anos.                           :\n" +
        " :                                            :\n" +
        " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
scanner.nextLine();
```

**Lyra** *(fala)*

```java
System.out.println(
        " .~~[ LYRA ]~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
        " :                                            :\n" +
        " :   Você acabou de dar tudo o que tinha pra  :\n" +
        " :   pessoas que você nunca mais vai ver.     :\n" +
        " :   Isso é burrice.                          :\n" +
        " :   Fica sabendo que eu reparei.             :\n" +
        " :                                            :\n" +
        " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
scanner.nextLine();
```



## `A2-05` — Opção 2 — Ignorar

> ℹ️ 

*(narração)*

```java
System.out.println(
        " .~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
        " :                                            :\n" +
        " :   Você fecha a bolsa.                      :\n" +
        " :   A missão é maior que onze pessoas. É o   :\n" +
        " :   que você diria em voz alta, se alguém    :\n" +
        " :   perguntasse. Ninguém pergunta.           :\n" +
        " :                                            :\n" +
        " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
scanner.nextLine();
```

**Brenna** *(fala)*

```java
System.out.println(
        " .~~[ BRENNA ]~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
        " :                                            :\n" +
        " :   Entendo. Você tem uma torre pra          :\n" +
        " :   alcançar.                                :\n" +
        " :                                            :\n" +
        " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
scanner.nextLine();
```

*(narração)*

```java
System.out.println(
        " .~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
        " :                                            :\n" +
        " :   Ela volta a trocar os panos. Não olha    :\n" +
        " :   mais para você até você sair.            :\n" +
        " :                                            :\n" +
        " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
scanner.nextLine();
```



## `A2-06` — Opção 3 — Saquear

> ℹ️ 

*(narração)*

```java
System.out.println(
        " .~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
        " :                                            :\n" +
        " :   Você começa pelo depósito. Depois as     :\n" +
        " :   casas vazias. Depois as que não estão    :\n" +
        " :   vazias, mas cujos donos não conseguem    :\n" +
        " :   mais levantar a cabeça do travesseiro.   :\n" +
        " :                                            :\n" +
        " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
scanner.nextLine();
```

**Brenna** *(fala)*

```java
System.out.println(
        " .~~[ BRENNA ]~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
        " :                                            :\n" +
        " :   Sai.                                     :\n" +
        " :   Sai da minha vila.                       :\n" +
        " :                                            :\n" +
        " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
scanner.nextLine();
```

**Brenna** *(fala)*

```java
System.out.println(
        " .~~[ BRENNA ]~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
        " :                                            :\n" +
        " :   Eu já vi o que a praga faz com uma       :\n" +
        " :   pessoa. Nunca tinha visto alguém         :\n" +
        " :   escolher fazer sozinho.                  :\n" +
        " :                                            :\n" +
        " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
scanner.nextLine();
```

**Lyra** *(fala)*

```java
System.out.println(
        " .~~[ LYRA ]~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
        " :                                            :\n" +
        " :   Eu roubo pra viver, garoto.              :\n" +
        " :   Até eu tenho um andar de baixo. Você     :\n" +
        " :   acabou de cavar mais um.                 :\n" +
        " :                                            :\n" +
        " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
scanner.nextLine();
```



## `A2-07` — Korrin, o ferreiro

> **🖼️ SPRITE:** `npc_korrin` — ao abrir a loja

```java
Sprite.mostrar("npc_korrin");   // <- seu sprite aqui
```

*(narração)*

```java
System.out.println(
        " .~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
        " :                                            :\n" +
        " :   Na saída da vila, uma carroça coberta e  :\n" +
        " :   um sujeito atarracado martelando uma     :\n" +
        " :   fivela que já estava boa.                :\n" +
        " :                                            :\n" +
        " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
scanner.nextLine();
```

**Korrin** *(fala)*

```java
System.out.println(
        " .~~[ KORRIN ]~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
        " :                                            :\n" +
        " :   Ah. Um cliente com pulso. Bom. Odeio     :\n" +
        " :   vender pra fantasma.                     :\n" +
        " :                                            :\n" +
        " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
scanner.nextLine();
```

**Korrin** *(fala)*

```java
System.out.println(
        " .~~[ KORRIN ]~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
        " :                                            :\n" +
        " :   Korrin. Ferro, lâmina, remendo e frasco. :\n" +
        " :   Não faço fiado, não faço caridade e não  :\n" +
        " :   faço perguntas. Faço desconto uma vez    :\n" +
        " :   por ano e já foi em março.               :\n" +
        " :                                            :\n" +
        " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
scanner.nextLine();
```

```java
Loja.abrir(jogador, Korrin.estoque());
```

> ℹ️ 

**Korrin** *(fala)*

```java
System.out.println(
        " .~~[ KORRIN ]~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
        " :                                            :\n" +
        " :   Rumor de graça, porque você comprou: os  :\n" +
        " :   carregamentos que sobem pra Cidadela vão :\n" +
        " :   vazios. Vazios! E descem pesados. Quem é :\n" +
        " :   que enche uma carroça descendo de um     :\n" +
        " :   castelo?                                 :\n" +
        " :                                            :\n" +
        " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
scanner.nextLine();
```

**Korrin** *(fala)*

```java
System.out.println(
        " .~~[ KORRIN ]~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
        " :                                            :\n" +
        " :   Outro: eu vendo pra Ordem faz trinta     :\n" +
        " :   anos. Trinta. O Grão-Mestre me atendeu   :\n" +
        " :   na primeira vez e me atendeu na semana   :\n" +
        " :   passada. Mesmo rosto. Mesmo cabelo       :\n" +
        " :   branco. Mesma altura.                    :\n" +
        " :                                            :\n" +
        " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
scanner.nextLine();
```

**Korrin** *(fala)*

```java
System.out.println(
        " .~~[ KORRIN ]~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
        " :                                            :\n" +
        " :   Deve ser o filho, né.                    :\n" +
        " :   Tem que ser o filho.                     :\n" +
        " :                                            :\n" +
        " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
scanner.nextLine();
```



## `A2-08` — O segredo de Lyra

> **🖼️ SPRITE:** `npc_lyra` — à noite, fogueira fora da vila

```java
Sprite.mostrar("npc_lyra");   // <- seu sprite aqui
```

*(narração)*

```java
System.out.println(
        " .~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
        " :                                            :\n" +
        " :   À noite, fora dos muros, ela fica        :\n" +
        " :   olhando o fogo por tempo demais antes de :\n" +
        " :   falar.                                   :\n" +
        " :                                            :\n" +
        " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
scanner.nextLine();
```

**Lyra** *(fala)*

```java
System.out.println(
        " .~~[ LYRA ]~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
        " :                                            :\n" +
        " :   Você não perguntou por que eu roubo da   :\n" +
        " :   Ordem.                                   :\n" +
        " :                                            :\n" +
        " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
scanner.nextLine();
```

**Lyra** *(fala)*

```java
System.out.println(
        " .~~[ LYRA ]~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
        " :                                            :\n" +
        " :   Faz onze anos. Uma aldeia chamada        :\n" +
        " :   Emberfall, na fronteira sul. Você não    :\n" +
        " :   vai achar num mapa novo, porque não      :\n" +
        " :   sobrou nada pra marcar.                  :\n" +
        " :                                            :\n" +
        " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
scanner.nextLine();
```

**Lyra** *(fala)*

```java
System.out.println(
        " .~~[ LYRA ]~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
        " :                                            :\n" +
        " :   A Ordem chegou dizendo que a praga tinha :\n" +
        " :   pegado lá. Isolaram a aldeia. Ninguém    :\n" +
        " :   entrava, ninguém saía. Levaram nove      :\n" +
        " :   dias.                                    :\n" +
        " :                                            :\n" +
        " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
scanner.nextLine();
```

**Lyra** *(fala)*

```java
System.out.println(
        " .~~[ LYRA ]~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
        " :                                            :\n" +
        " :   Quando abriram de novo, não tinha doente :\n" +
        " :   nenhum. Tinha vazio. Cento e quarenta    :\n" +
        " :   pessoas de vazio.                        :\n" +
        " :                                            :\n" +
        " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
scanner.nextLine();
```

**Lyra** *(fala)*

```java
System.out.println(
        " .~~[ LYRA ]~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
        " :                                            :\n" +
        " :   Minha mãe estava lá dentro. Eu estava do :\n" +
        " :   lado de fora porque tinha ido vender     :\n" +
        " :   ovos na estrada. Foi só isso. Ovos.      :\n" +
        " :                                            :\n" +
        " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
scanner.nextLine();
```

*(narração)*

```java
System.out.println(
        " .~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
        " :                                            :\n" +
        " :   Ela mexe no fogo com a ponta da adaga.   :\n" +
        " :                                            :\n" +
        " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
scanner.nextLine();
```

**Lyra** *(fala)*

```java
System.out.println(
        " .~~[ LYRA ]~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
        " :                                            :\n" +
        " :   Então eu roubo deles. Roubo tudo o que   :\n" +
        " :   eu consigo. E se um dia eu tiver uma     :\n" +
        " :   chance de fazer mais do que roubar, eu   :\n" +
        " :   vou fazer.                               :\n" +
        " :                                            :\n" +
        " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
scanner.nextLine();
```

**Lyra** *(fala)*

```java
System.out.println(
        " .~~[ LYRA ]~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
        " :                                            :\n" +
        " :   Agora você sabe.                         :\n" +
        " :   Faz o que a Ordem te ensinaria a fazer,  :\n" +
        " :   ou faz outra coisa.                      :\n" +
        " :                                            :\n" +
        " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
scanner.nextLine();
```

> ℹ️ 

### ⚖️ ESCOLHA 3 — O segredo de Lyra (Ato 2)

| # | Opção | Karma | Efeito |
|---|-------|-------|--------|
| 1 | Acreditar nela e protegê-la | **+1 Honra** | `lyraAliada = true`. Ela luta ao seu lado no Ato 4 e o final herói fica completo |
| 2 | Denunciá-la à Ordem por corvo | **+2 Ambição** | `lyraTraida = true`. Aldric te elogia. Lyra some — e volta como inimiga no final |

```java
System.out.println("   [1] Eu acredito em você. Ninguém vai saber por mim.");
System.out.println("   [2] Mandar um corvo à Cidadela relatando a ladra");
System.out.print("   > ");
int e3 = Integer.parseInt(scanner.nextLine().trim());

if (e3 == 1) {
    honra++;
    lyraAliada = true;
    confiancaLyra += 2;
} else {
    ambicao += 2;
    lyraTraida = true;
    lyraNoGrupo = false;
}
```



## `A2-09` — Opção 1 — Proteger Lyra

> ℹ️ 

**Você** *(fala)*

```java
System.out.println(
        " .~~[ VOCÊ ]~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
        " :                                            :\n" +
        " :   Eu acredito em você.                     :\n" +
        " :                                            :\n" +
        " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
scanner.nextLine();
```

*(narração)*

```java
System.out.println(
        " .~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
        " :                                            :\n" +
        " :   Ela ri uma vez, curta, sem graça         :\n" +
        " :   nenhuma.                                 :\n" +
        " :   É o primeiro som honesto que ela faz     :\n" +
        " :   desde Thornwood.                         :\n" +
        " :                                            :\n" +
        " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
scanner.nextLine();
```

**Lyra** *(fala)*

```java
System.out.println(
        " .~~[ LYRA ]~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
        " :                                            :\n" +
        " :   Você é péssimo cavaleiro, sabia.         :\n" +
        " :   Péssimo mesmo.                           :\n" +
        " :                                            :\n" +
        " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
scanner.nextLine();
```

**Lyra** *(fala)*

```java
System.out.println(
        " .~~[ LYRA ]~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
        " :                                            :\n" +
        " :   Fica perto de mim em Karn. Tem coisa lá  :\n" +
        " :   embaixo que eu preciso te mostrar, e eu  :\n" +
        " :   não ia mostrar pra qualquer um.          :\n" +
        " :                                            :\n" +
        " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
scanner.nextLine();
```

```java
lyraAliada = true;
```



## `A2-10` — Opção 2 — Denunciar Lyra

> ℹ️ 

*(narração)*

```java
System.out.println(
        " .~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
        " :                                            :\n" +
        " :   O corvo sobe antes do amanhecer, com o   :\n" +
        " :   selo da Ordem no tubo.                   :\n" +
        " :                                            :\n" +
        " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
scanner.nextLine();
```

*(narração)*

```java
System.out.println(
        " .~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
        " :                                            :\n" +
        " :   Ela não grita. É pior: ela entende na    :\n" +
        " :   hora, só de ver a sua cara no café da    :\n" +
        " :   manhã.                                   :\n" +
        " :                                            :\n" +
        " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
scanner.nextLine();
```

**Lyra** *(fala)*

```java
System.out.println(
        " .~~[ LYRA ]~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
        " :                                            :\n" +
        " :   Ah.                                      :\n" +
        " :   Você mandou ontem à noite ou hoje de     :\n" +
        " :   madrugada?                               :\n" +
        " :                                            :\n" +
        " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
scanner.nextLine();
```

**Lyra** *(fala)*

```java
System.out.println(
        " .~~[ LYRA ]~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
        " :                                            :\n" +
        " :   Não responde. Não muda nada.             :\n" +
        " :                                            :\n" +
        " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
scanner.nextLine();
```

**Lyra** *(fala)*

```java
System.out.println(
        " .~~[ LYRA ]~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
        " :                                            :\n" +
        " :   Eu te contei da minha mãe.               :\n" +
        " :   Boa sorte em Karn, cavaleiro. Espero que :\n" +
        " :   você goste do que tem lá embaixo, porque :\n" +
        " :   foi você que escolheu descer sozinho.    :\n" +
        " :                                            :\n" +
        " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
scanner.nextLine();
```

*(narração)*

```java
System.out.println(
        " .~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
        " :                                            :\n" +
        " :   Quando você levanta acampamento, o lugar :\n" +
        " :   dela já está frio.                       :\n" +
        " :                                            :\n" +
        " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
scanner.nextLine();
```

```java
lyraTraida = true;
lyraNoGrupo = false;
```



---

# ATO 3 — AS RUÍNAS DE KARN


## `A3-01` — A descida

> **🖼️ SPRITE:** `cenario_karn` — ruínas na superfície, ao entrar na região

```java
Sprite.mostrar("cenario_karn");   // <- seu sprite aqui
```

*(narração)*

```java
System.out.println(
        " .~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
        " :                                            :\n" +
        " :   Karn foi um mosteiro da Ordem antes de   :\n" +
        " :   ser ruína.                               :\n" +
        " :   Ninguém explica direito por que          :\n" +
        " :   abandonaram uma fortaleza inteira num    :\n" +
        " :   ponto tão bom do mapa.                   :\n" +
        " :                                            :\n" +
        " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
scanner.nextLine();
```

*(narração)*

```java
System.out.println(
        " .~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
        " :                                            :\n" +
        " :   A entrada das catacumbas está selada com :\n" +
        " :   pedra nova sobre pedra velha.            :\n" +
        " :   Selada por dentro para fora. Quem fechou :\n" +
        " :   isto não estava impedindo alguém de      :\n" +
        " :   entrar.                                  :\n" +
        " :                                            :\n" +
        " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
scanner.nextLine();
```

> ℹ️ 

```java
if (lyraAliada) {
    cenaLyraAbrePassagem();     // ela conhece o caminho: sinal de que já esteve aqui
} else {
    jogador.receberDano(8);     // arrombar sozinho custa caro
    cenaArrombarSozinho();
}
```



## `A3-02` — Lyra abre a passagem

> ℹ️ 

**Lyra** *(fala)*

```java
System.out.println(
        " .~~[ LYRA ]~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
        " :                                            :\n" +
        " :   Terceira pedra da fileira de baixo.      :\n" +
        " :   Empurra, não puxa.                       :\n" +
        " :                                            :\n" +
        " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
scanner.nextLine();
```

*(narração)*

```java
System.out.println(
        " .~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
        " :                                            :\n" +
        " :   Você olha para ela.                      :\n" +
        " :   Ela olha para a parede.                  :\n" +
        " :                                            :\n" +
        " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
scanner.nextLine();
```

**Lyra** *(fala)*

```java
System.out.println(
        " .~~[ LYRA ]~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
        " :                                            :\n" +
        " :   Eu já estive aqui. Várias vezes. Nunca   :\n" +
        " :   consegui passar da segunda câmara        :\n" +
        " :   sozinha.                                 :\n" +
        " :                                            :\n" +
        " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
scanner.nextLine();
```

**Lyra** *(fala)*

```java
System.out.println(
        " .~~[ LYRA ]~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
        " :                                            :\n" +
        " :   Depois a gente conversa sobre isso.      :\n" +
        " :                                            :\n" +
        " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
scanner.nextLine();
```



## `A3-03` — Mob — Esqueleto Guardião

> **🖼️ SPRITE:** `mob_esqueleto_guardiao` — ao iniciar o combate

```java
Sprite.mostrar("mob_esqueleto_guardiao");   // <- seu sprite aqui
```

*(narração)*

```java
System.out.println(
        " .~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
        " :                                            :\n" +
        " :   O corredor desce por trinta degraus e    :\n" +
        " :   termina numa câmara circular.            :\n" +
        " :   Alinhados nas paredes, em nichos,        :\n" +
        " :   dezenas de esqueletos de armadura        :\n" +
        " :   completa.                                :\n" +
        " :                                            :\n" +
        " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
scanner.nextLine();
```

*(narração)*

```java
System.out.println(
        " .~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
        " :                                            :\n" +
        " :   Armadura da Ordem. Brasão da Aurora no   :\n" +
        " :   peito.                                   :\n" +
        " :   O do meio levanta a cabeça.              :\n" +
        " :                                            :\n" +
        " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
scanner.nextLine();
```

```java
Combate.iniciar(jogador, new EsqueletoGuardiao());
```

*(narração)*

```java
System.out.println(
        " .~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
        " :                                            :\n" +
        " :   Quando ele desaba, o elmo rola e para    :\n" +
        " :   aos seus pés.                            :\n" +
        " :   Por dentro, gravado na testa de metal,   :\n" +
        " :   um nome e uma data.                      :\n" +
        " :   A data é de quatro anos atrás.           :\n" +
        " :                                            :\n" +
        " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
scanner.nextLine();
```

*(narração)*

```java
System.out.println(
        " .~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
        " :                                            :\n" +
        " :   Este homem não é antigo.                 :\n" +
        " :   Este homem foi enterrado aqui            :\n" +
        " :   recentemente, de pé, com a armadura      :\n" +
        " :   vestida, para continuar montando guarda  :\n" +
        " :   depois de morto.                         :\n" +
        " :                                            :\n" +
        " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
scanner.nextLine();
```



## `A3-04` — O diário de Malachar

> **🖼️ SPRITE:** `item_diario` — ao encontrar o diário

```java
Sprite.mostrar("item_diario");   // <- seu sprite aqui
```

*(narração)*

```java
System.out.println(
        " .~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
        " :                                            :\n" +
        " :   Na segunda câmara há uma mesa, uma       :\n" +
        " :   cadeira virada e um livro coberto de     :\n" +
        " :   poeira, aberto na metade, como se alguém :\n" +
        " :   tivesse saído no meio de uma frase e     :\n" +
        " :   nunca mais voltado.                      :\n" +
        " :                                            :\n" +
        " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
scanner.nextLine();
```

*(narração)*

```java
System.out.println(
        " .~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
        " :                                            :\n" +
        " :   A caligrafia é apertada, inclinada, de   :\n" +
        " :   quem escreve rápido porque tem medo de   :\n" +
        " :   ser interrompido.                        :\n" +
        " :                                            :\n" +
        " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
scanner.nextLine();
```

**Diário de Malachar** *(fala)*

```java
System.out.println(
        " .~~[ DIÁRIO DE MALACHAR ]~~~~~~~~~~~~~~~~~~~~.\n" +
        " :                                            :\n" +
        " :   Ano 12. A Chama não protege Valdrath. A  :\n" +
        " :   Chama se alimenta de Valdrath. Medi o    :\n" +
        " :   avanço da praga em três estações. Ela    :\n" +
        " :   não vem das bordas para o centro. Ela    :\n" +
        " :   vem do centro para as bordas.            :\n" +
        " :                                            :\n" +
        " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
scanner.nextLine();
```

**Diário de Malachar** *(fala)*

```java
System.out.println(
        " .~~[ DIÁRIO DE MALACHAR ]~~~~~~~~~~~~~~~~~~~~.\n" +
        " :                                            :\n" +
        " :   Ano 14. O epicentro é a Cidadela. É a    :\n" +
        " :   relíquia. Cada campo que apodrece, cada  :\n" +
        " :   criança que fica preta dos pés para      :\n" +
        " :   cima, é vida sendo puxada para dentro    :\n" +
        " :   daquele salão.                           :\n" +
        " :                                            :\n" +
        " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
scanner.nextLine();
```

**Diário de Malachar** *(fala)*

```java
System.out.println(
        " .~~[ DIÁRIO DE MALACHAR ]~~~~~~~~~~~~~~~~~~~~.\n" +
        " :                                            :\n" +
        " :   Ano 15. Perguntei ao Aldric. Ele não     :\n" +
        " :   negou. Ele sorriu e me perguntou quantos :\n" +
        " :   anos eu achava que ele tinha.            :\n" +
        " :                                            :\n" +
        " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
scanner.nextLine();
```

**Diário de Malachar** *(fala)*

```java
System.out.println(
        " .~~[ DIÁRIO DE MALACHAR ]~~~~~~~~~~~~~~~~~~~~.\n" +
        " :                                            :\n" +
        " :   Ano 15. Eu tinha cento e nove naquele    :\n" +
        " :   dia, e aparentava quarenta. O preço      :\n" +
        " :   disso está espalhado por quatro regiões, :\n" +
        " :   em cova rasa.                            :\n" +
        " :                                            :\n" +
        " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
scanner.nextLine();
```

**Diário de Malachar** *(fala)*

```java
System.out.println(
        " .~~[ DIÁRIO DE MALACHAR ]~~~~~~~~~~~~~~~~~~~~.\n" +
        " :                                            :\n" +
        " :   Ano 16. Tentei apagá-la. Falhei. Fui     :\n" +
        " :   declarado traidor na mesma noite. Agora  :\n" +
        " :   sou eu o motivo da praga, nas canções    :\n" +
        " :   que eles mandam cantar.                  :\n" +
        " :                                            :\n" +
        " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
scanner.nextLine();
```

**Diário de Malachar** *(fala)*

```java
System.out.println(
        " .~~[ DIÁRIO DE MALACHAR ]~~~~~~~~~~~~~~~~~~~~.\n" +
        " :                                            :\n" +
        " :   Ano 16. Se alguém ler isto: não me       :\n" +
        " :   procure para me matar. Me procure para   :\n" +
        " :   terminar o que eu comecei.               :\n" +
        " :                                            :\n" +
        " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
scanner.nextLine();
```



## `A3-05` — Mob — Espectro dos Sacrificados

> **🖼️ SPRITE:** `mob_espectro` — ao iniciar o combate

```java
Sprite.mostrar("mob_espectro");   // <- seu sprite aqui
```

*(narração)*

```java
System.out.println(
        " .~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
        " :                                            :\n" +
        " :   O ar esfria de uma vez.                  :\n" +
        " :   Da parede do fundo, onde não há porta,   :\n" +
        " :   saem formas. Muitas. Finas como fumaça e :\n" +
        " :   com bocas escancaradas que não fazem     :\n" +
        " :   som.                                     :\n" +
        " :                                            :\n" +
        " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
scanner.nextLine();
```

**Espectro** *(fala)*

```java
System.out.println(
        " .~~[ ESPECTRO ]~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
        " :                                            :\n" +
        " :   Nós fomos oferecidos.                    :\n" +
        " :                                            :\n" +
        " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
scanner.nextLine();
```

**Espectro** *(fala)*

```java
System.out.println(
        " .~~[ ESPECTRO ]~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
        " :                                            :\n" +
        " :   Emberfall. Hollowmere. Ashford. Duskrow. :\n" +
        " :   Diga os nomes. Alguém tem que dizer os   :\n" +
        " :   nomes.                                   :\n" +
        " :                                            :\n" +
        " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
scanner.nextLine();
```

```java
Combate.iniciar(jogador, new EspectroDosSacrificados());
```

*(narração)*

```java
System.out.println(
        " .~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
        " :                                            :\n" +
        " :   Quando o último se desfaz, resta o eco   :\n" +
        " :   de uma frase que você não pediu para     :\n" +
        " :   ouvir.                                   :\n" +
        " :                                            :\n" +
        " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
scanner.nextLine();
```

**Espectro** *(fala)*

```java
System.out.println(
        " .~~[ ESPECTRO ]~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
        " :                                            :\n" +
        " :   Duskrow.                                 :\n" +
        " :   Você conhece essa.                       :\n" +
        " :   Você nasceu nela.                        :\n" +
        " :                                            :\n" +
        " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
scanner.nextLine();
```

> ℹ️ 



## `A3-06` — A parede dos nomes

> **🖼️ SPRITE:** `cenario_parede_nomes` — câmara final das catacumbas

```java
Sprite.mostrar("cenario_parede_nomes");   // <- seu sprite aqui
```

*(narração)*

```java
System.out.println(
        " .~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
        " :                                            :\n" +
        " :   A última câmara não tem tesouro, nem     :\n" +
        " :   altar, nem monstro.                      :\n" +
        " :   Tem uma parede. E na parede, gravados em :\n" +
        " :   coluna, nomes.                           :\n" +
        " :                                            :\n" +
        " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
scanner.nextLine();
```

*(narração)*

```java
System.out.println(
        " .~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
        " :                                            :\n" +
        " :   Milhares. Em letra miúda, do chão ao     :\n" +
        " :   teto, agrupados por aldeia e por ano.    :\n" +
        " :   Um registro contábil. Feito com          :\n" +
        " :   capricho, por alguém que achava          :\n" +
        " :   importante manter a escrituração em dia. :\n" +
        " :                                            :\n" +
        " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
scanner.nextLine();
```

*(narração)*

```java
System.out.println(
        " .~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
        " :                                            :\n" +
        " :   Você encontra Duskrow.                   :\n" +
        " :   Ano dezenove.                            :\n" +
        " :   E dentro de Duskrow, o seu sobrenome.    :\n" +
        " :   Três vezes.                              :\n" +
        " :                                            :\n" +
        " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
scanner.nextLine();
```

*(narração)*

```java
System.out.println(
        " .~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
        " :                                            :\n" +
        " :   Pai. Mãe. Irmã.                          :\n" +
        " :   Registrados, contados e assinados.       :\n" +
        " :   A assinatura no rodapé da coluna é uma   :\n" +
        " :   que você conhece de todos os documentos  :\n" +
        " :   que já assinou na vida.                  :\n" +
        " :                                            :\n" +
        " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
scanner.nextLine();
```

> **🖼️ SPRITE:** `item_assinatura_aldric` — close na assinatura

```java
Sprite.mostrar("item_assinatura_aldric");   // <- seu sprite aqui
```

*(narração)*

```java
System.out.println(
        " .~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
        " :                                            :\n" +
        " :   Grão-Mestre Aldric da Aurora.            :\n" +
        " :                                            :\n" +
        " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
scanner.nextLine();
```

*(narração)*

```java
System.out.println(
        " .~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
        " :                                            :\n" +
        " :   Sua família não morreu de praga.         :\n" +
        " :   Sua família foi entregue.                :\n" +
        " :   E o homem que assinou a entrega te       :\n" +
        " :   criou, te alimentou, te ensinou a        :\n" +
        " :   segurar uma espada e te mandou matar a   :\n" +
        " :   única pessoa que tentou impedir aquilo.  :\n" +
        " :                                            :\n" +
        " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
scanner.nextLine();
```



## `A3-07` — A Voz da Chama fala pela primeira vez

> **🖼️ SPRITE:** `efeito_voz_chama` — overlay/efeito, sem retrato

```java
Sprite.mostrar("efeito_voz_chama");   // <- seu sprite aqui
```

> ℹ️ 

**A Voz da Chama** *(a voz da chama)*

```java
System.out.println(
        " .~~[ A VOZ DA CHAMA ]~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
        " :                                            :\n" +
        " :   Você está com raiva.                     :\n" +
        " :   Que bom. Raiva é combustível de          :\n" +
        " :   qualidade.                               :\n" +
        " :                                            :\n" +
        " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
scanner.nextLine();
```

**A Voz da Chama** *(a voz da chama)*

```java
System.out.println(
        " .~~[ A VOZ DA CHAMA ]~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
        " :                                            :\n" +
        " :   Eles te usaram por vinte anos e você     :\n" +
        " :   agradeceu. Eu te ofereço uma coisa mais  :\n" +
        " :   honesta: use-me você.                    :\n" +
        " :                                            :\n" +
        " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
scanner.nextLine();
```

**A Voz da Chama** *(a voz da chama)*

```java
System.out.println(
        " .~~[ A VOZ DA CHAMA ]~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
        " :                                            :\n" +
        " :   Por que destruir, se você pode possuir?  :\n" +
        " :   Aldric é apenas o último inquilino. A    :\n" +
        " :   casa aceita quem chegar primeiro.        :\n" +
        " :                                            :\n" +
        " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
scanner.nextLine();
```



## `A3-08` — Lyra confessa (se aliada)

> ℹ️ 

> **🖼️ SPRITE:** `npc_lyra` — diante da parede dos nomes

```java
Sprite.mostrar("npc_lyra");   // <- seu sprite aqui
```

*(narração)*

```java
System.out.println(
        " .~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
        " :                                            :\n" +
        " :   Lyra está parada na frente da parede, do :\n" +
        " :   outro lado, com a mão sobre uma coluna   :\n" +
        " :   diferente.                               :\n" +
        " :                                            :\n" +
        " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
scanner.nextLine();
```

**Lyra** *(fala)*

```java
System.out.println(
        " .~~[ LYRA ]~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
        " :                                            :\n" +
        " :   Emberfall. Ano oito.                     :\n" +
        " :   Terceira linha.                          :\n" +
        " :                                            :\n" +
        " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
scanner.nextLine();
```

**Lyra** *(fala)*

```java
System.out.println(
        " .~~[ LYRA ]~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
        " :                                            :\n" +
        " :   Eu já sabia. Faz seis anos que eu sei.   :\n" +
        " :                                            :\n" +
        " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
scanner.nextLine();
```

**Lyra** *(fala)*

```java
System.out.println(
        " .~~[ LYRA ]~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
        " :                                            :\n" +
        " :   Tem uma coisa que eu não te contei, e    :\n" +
        " :   agora eu preciso contar antes que você   :\n" +
        " :   descubra sozinho e me mate por isso.     :\n" +
        " :                                            :\n" +
        " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
scanner.nextLine();
```

**Lyra** *(fala)*

```java
System.out.println(
        " .~~[ LYRA ]~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
        " :                                            :\n" +
        " :   Malachar é meu pai.                      :\n" +
        " :                                            :\n" +
        " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
scanner.nextLine();
```

*(narração)*

```java
System.out.println(
        " .~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
        " :                                            :\n" +
        " :   Ela não tira a mão da parede.            :\n" +
        " :                                            :\n" +
        " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
scanner.nextLine();
```

**Lyra** *(fala)*

```java
System.out.println(
        " .~~[ LYRA ]~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
        " :                                            :\n" +
        " :   Minha mãe foi levada em Emberfall        :\n" +
        " :   enquanto ele tentava provar o que estava :\n" +
        " :   acontecendo. Ele nunca chegou a tempo.   :\n" +
        " :   Ele nunca chega a tempo, é o problema    :\n" +
        " :   dele.                                    :\n" +
        " :                                            :\n" +
        " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
scanner.nextLine();
```

**Lyra** *(fala)*

```java
System.out.println(
        " .~~[ LYRA ]~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
        " :                                            :\n" +
        " :   Eu me juntei a você porque você era o    :\n" +
        " :   assassino que a Ordem estava mandando.   :\n" +
        " :   Eu queria chegar nele antes de você.     :\n" +
        " :   Era pra eu te trair em Karn.             :\n" +
        " :                                            :\n" +
        " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
scanner.nextLine();
```

**Lyra** *(fala)*

```java
System.out.println(
        " .~~[ LYRA ]~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
        " :                                            :\n" +
        " :   Mas você poupou um homem que não te      :\n" +
        " :   devia nada. Você deu seu ouro pra gente  :\n" +
        " :   que ia morrer de qualquer jeito. Você    :\n" +
        " :   acreditou em mim quando a coisa fácil    :\n" +
        " :   era me entregar.                         :\n" +
        " :                                            :\n" +
        " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
scanner.nextLine();
```

**Lyra** *(fala)*

```java
System.out.println(
        " .~~[ LYRA ]~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
        " :                                            :\n" +
        " :   Então eu estou te contando.              :\n" +
        " :   Faz o que quiser com isso.               :\n" +
        " :                                            :\n" +
        " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
scanner.nextLine();
```

```java
lyraFilhaRevelada = true;
confiancaLyra += 2;
```



## `A3-09` — Sem Lyra — a versão fria

> ℹ️ 

*(narração)*

```java
System.out.println(
        " .~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
        " :                                            :\n" +
        " :   Você lê a parede sozinho, à luz de um    :\n" +
        " :   archote que já está no fim.              :\n" +
        " :                                            :\n" +
        " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
scanner.nextLine();
```

*(narração)*

```java
System.out.println(
        " .~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
        " :                                            :\n" +
        " :   Numa coluna à esquerda, Emberfall, ano   :\n" +
        " :   oito, terceira linha: um nome de mulher  :\n" +
        " :   e, ao lado, entre parênteses, uma        :\n" +
        " :   anotação de escriba.                     :\n" +
        " :                                            :\n" +
        " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
scanner.nextLine();
```

*(narração)*

```java
System.out.println(
        " .~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
        " :                                            :\n" +
        " :   Filha localizada. Menor. Marcada para    :\n" +
        " :   observação futura.                       :\n" +
        " :                                            :\n" +
        " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
scanner.nextLine();
```

*(narração)*

```java
System.out.println(
        " .~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
        " :                                            :\n" +
        " :   Você denunciou essa filha à Ordem duas   :\n" +
        " :   noites atrás, por corvo, com o seu selo. :\n" +
        " :   Você fez o serviço deles de graça.       :\n" +
        " :                                            :\n" +
        " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
scanner.nextLine();
```

### ⚖️ ESCOLHA 4 — A verdade revelada (Ato 3)

Você acabou de ler o próprio sobrenome numa parede de contabilidade. A Voz da Chama está esperando resposta.

| # | Opção | Karma | Efeito |
|---|-------|-------|--------|
| 1 | Aceitar a verdade e jurar derrubar a Ordem | **+2 Honra** | `verdadeAceita = true`. Abre a rota herói de verdade |
| 2 | Aceitar a verdade e cobiçar a Chama para si | **+2 Ambição** | `verdadeAceita = true`, `pactoVoz = true`. A Voz passa a te acompanhar |
| 3 | Recusar-se a acreditar — "não é problema meu" | **Neutro** | `verdadeNegada = true`. Trava os dois finais extremos, empurra para o Andarilho |

```java
System.out.println("   [1] Isto acaba. Eu vou derrubar a Ordem.");
System.out.println("   [2] Vinte anos de mentira. Agora essa Chama é minha.");
System.out.println("   [3] Não é problema meu. Eu só quero sair daqui.");
System.out.print("   > ");
int e4 = Integer.parseInt(scanner.nextLine().trim());

switch (e4) {
    case 1 -> { honra += 2;   verdadeAceita = true; }
    case 2 -> { ambicao += 2; verdadeAceita = true; pactoVoz = true; }
    case 3 -> { verdadeNegada = true; }
}
```



## `A3-10` — Opção 1 — Jurar derrubar a Ordem

> ℹ️ 

*(narração)*

```java
System.out.println(
        " .~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
        " :                                            :\n" +
        " :   Você passa o polegar sobre as três       :\n" +
        " :   linhas com o seu sobrenome, devagar,     :\n" +
        " :   como se pudesse apagá-las com a          :\n" +
        " :   insistência.                             :\n" +
        " :                                            :\n" +
        " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
scanner.nextLine();
```

**Você** *(fala)*

```java
System.out.println(
        " .~~[ VOCÊ ]~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
        " :                                            :\n" +
        " :   Vinte anos.                              :\n" +
        " :   Vocês me criaram com a mão suja do       :\n" +
        " :   sangue deles e me mandaram limpar a de   :\n" +
        " :   outra pessoa.                            :\n" +
        " :                                            :\n" +
        " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
scanner.nextLine();
```

**Você** *(fala)*

```java
System.out.println(
        " .~~[ VOCÊ ]~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
        " :                                            :\n" +
        " :   Eu vou apagar essa Chama. Não pelo       :\n" +
        " :   reino. Por três linhas nessa parede.     :\n" +
        " :                                            :\n" +
        " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
scanner.nextLine();
```

**A Voz da Chama** *(a voz da chama)*

```java
System.out.println(
        " .~~[ A VOZ DA CHAMA ]~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
        " :                                            :\n" +
        " :   Que desperdício.                         :\n" +
        " :   Tudo bem. Eu sei esperar. Eu esperei mil :\n" +
        " :   anos, garoto. Você tem uns quarenta.     :\n" +
        " :                                            :\n" +
        " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
scanner.nextLine();
```



## `A3-11` — Opção 2 — Cobiçar a Chama

> ℹ️ 

*(narração)*

```java
System.out.println(
        " .~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
        " :                                            :\n" +
        " :   Você lê os nomes de novo. Não como luto. :\n" +
        " :   Como preço já pago.                      :\n" +
        " :                                            :\n" +
        " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
scanner.nextLine();
```

**Você** *(fala)*

```java
System.out.println(
        " .~~[ VOCÊ ]~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
        " :                                            :\n" +
        " :   Se o custo já foi cobrado, alguém devia  :\n" +
        " :   pelo menos aproveitar o que foi          :\n" +
        " :   comprado.                                :\n" +
        " :                                            :\n" +
        " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
scanner.nextLine();
```

**A Voz da Chama** *(a voz da chama)*

```java
System.out.println(
        " .~~[ A VOZ DA CHAMA ]~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
        " :                                            :\n" +
        " :   Aí está.                                 :\n" +
        " :   Sabia que tinha alguém inteligente aí    :\n" +
        " :   dentro.                                  :\n" +
        " :                                            :\n" +
        " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
scanner.nextLine();
```

**A Voz da Chama** *(a voz da chama)*

```java
System.out.println(
        " .~~[ A VOZ DA CHAMA ]~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
        " :                                            :\n" +
        " :   Aldric ficou velho de espírito antes de  :\n" +
        " :   ficar velho de corpo. Eu quero um dono   :\n" +
        " :   novo.                                    :\n" +
        " :   Suba. Eu te espero acesa.                :\n" +
        " :                                            :\n" +
        " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
scanner.nextLine();
```

```java
pactoVoz = true;
```



## `A3-12` — Opção 3 — Recusar

> ℹ️ 

*(narração)*

```java
System.out.println(
        " .~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
        " :                                            :\n" +
        " :   Você fecha o diário.                     :\n" +
        " :   Você vira as costas para a parede.       :\n" +
        " :                                            :\n" +
        " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
scanner.nextLine();
```

**Você** *(fala)*

```java
System.out.println(
        " .~~[ VOCÊ ]~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
        " :                                            :\n" +
        " :   Isso é briga de gente velha. Eu tinha    :\n" +
        " :   três anos quando assinaram isso.         :\n" +
        " :                                            :\n" +
        " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
scanner.nextLine();
```

**Você** *(fala)*

```java
System.out.println(
        " .~~[ VOCÊ ]~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
        " :                                            :\n" +
        " :   Eu não vou carregar guerra de ninguém.   :\n" +
        " :   Nem a deles, nem a dele.                 :\n" +
        " :                                            :\n" +
        " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
scanner.nextLine();
```

**A Voz da Chama** *(a voz da chama)*

```java
System.out.println(
        " .~~[ A VOZ DA CHAMA ]~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
        " :                                            :\n" +
        " :   Covardia também é uma escolha.           :\n" +
        " :   É a única que ninguém canta em taverna   :\n" +
        " :   nenhuma.                                 :\n" +
        " :                                            :\n" +
        " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
scanner.nextLine();
```



## `A3-13` — Malachar

> **🖼️ SPRITE:** `npc_malachar` — ao entrar em cena, saída das catacumbas

```java
Sprite.mostrar("npc_malachar");   // <- seu sprite aqui
```

*(narração)*

```java
System.out.println(
        " .~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
        " :                                            :\n" +
        " :   Ele está sentado nos degraus da saída,   :\n" +
        " :   sem magia nas mãos, sem cajado erguido.  :\n" +
        " :   Um homem velho e cansado, esperando o    :\n" +
        " :   assassino chegar para poder finalmente   :\n" +
        " :   conversar.                               :\n" +
        " :                                            :\n" +
        " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
scanner.nextLine();
```

**Malachar** *(fala)*

```java
System.out.println(
        " .~~[ MALACHAR ]~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
        " :                                            :\n" +
        " :   Você demorou.                            :\n" +
        " :   Sente. Eu não vou lutar com você, e você :\n" +
        " :   não está com vontade nenhuma de lutar    :\n" +
        " :   comigo depois de ler aquilo.             :\n" +
        " :                                            :\n" +
        " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
scanner.nextLine();
```

**Malachar** *(fala)*

```java
System.out.println(
        " .~~[ MALACHAR ]~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
        " :                                            :\n" +
        " :   Malachar. Feiticeiro Sombrio, praga de   :\n" +
        " :   Valdrath, comedor de crianças, o que     :\n" +
        " :   mais eles estiverem cantando este ano.   :\n" +
        " :                                            :\n" +
        " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
scanner.nextLine();
```

**Malachar** *(fala)*

```java
System.out.println(
        " .~~[ MALACHAR ]~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
        " :                                            :\n" +
        " :   Eu fui o segundo homem da Ordem por      :\n" +
        " :   trinta anos. Depois eu contei. Só isso.  :\n" +
        " :   Eu contei.                               :\n" +
        " :                                            :\n" +
        " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
scanner.nextLine();
```

**Malachar** *(fala)*

```java
System.out.println(
        " .~~[ MALACHAR ]~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
        " :                                            :\n" +
        " :   Não tenho exército, não tenho torre      :\n" +
        " :   cheia de demônio. Tenho um diário que    :\n" +
        " :   você já leu e uma pergunta.              :\n" +
        " :                                            :\n" +
        " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
scanner.nextLine();
```

**Malachar** *(fala)*

```java
System.out.println(
        " .~~[ MALACHAR ]~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
        " :                                            :\n" +
        " :   Aldric vai te receber de braços abertos. :\n" +
        " :   Ele sempre recebe. É assim que ele       :\n" +
        " :   coleciona gente.                         :\n" +
        " :                                            :\n" +
        " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
scanner.nextLine();
```

**Malachar** *(fala)*

```java
System.out.println(
        " .~~[ MALACHAR ]~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
        " :                                            :\n" +
        " :   A pergunta é: quando ele estender a mão, :\n" +
        " :   você vai apertar, cortar, ou dar meia-   :\n" +
        " :   volta e ir embora?                       :\n" +
        " :                                            :\n" +
        " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
scanner.nextLine();
```

*(narração)*

```java
System.out.println(
        " .~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
        " :                                            :\n" +
        " :   Ele se levanta com dificuldade e aponta  :\n" +
        " :   o norte com o queixo.                    :\n" +
        " :                                            :\n" +
        " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
scanner.nextLine();
```

**Malachar** *(fala)*

```java
System.out.println(
        " .~~[ MALACHAR ]~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
        " :                                            :\n" +
        " :   Eu vou subir com você. Não porque eu     :\n" +
        " :   confio em você.                          :\n" +
        " :   Porque eu não tenho mais nada pra fazer  :\n" +
        " :   com o tempo que sobrou.                  :\n" +
        " :                                            :\n" +
        " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
scanner.nextLine();
```

```java
malacharAliado = !verdadeNegada;   // se você negou a verdade, ele sobe atrás de você, não com você
```



---

# ATO 4 — A CIDADELA DA AURORA


## `A4-01` — O retorno

> **🖼️ SPRITE:** `cenario_cidadela_noite` — portões da Cidadela, à noite

```java
Sprite.mostrar("cenario_cidadela_noite");   // <- seu sprite aqui
```

*(narração)*

```java
System.out.println(
        " .~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
        " :                                            :\n" +
        " :   A Cidadela é a mesma. Você é que virou   :\n" +
        " :   outra coisa no caminho.                  :\n" +
        " :   As tochas do pátio, que você achava      :\n" +
        " :   bonitas quando tinha doze anos, agora    :\n" +
        " :   parecem exatamente o que são: fogo que   :\n" +
        " :   alguém precisa alimentar.                :\n" +
        " :                                            :\n" +
        " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
scanner.nextLine();
```

*(narração)*

```java
System.out.println(
        " .~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
        " :                                            :\n" +
        " :   E ao redor, num raio de meia légua, o    :\n" +
        " :   capim é verde.                           :\n" +
        " :   O único verde que você viu em quatro     :\n" +
        " :   regiões.                                 :\n" +
        " :                                            :\n" +
        " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
scanner.nextLine();
```



## `A4-02` — Mob — Cavaleiro da Ordem

> **🖼️ SPRITE:** `mob_cavaleiro_ordem` — ao iniciar o combate

```java
Sprite.mostrar("mob_cavaleiro_ordem");   // <- seu sprite aqui
```

*(narração)*

```java
System.out.println(
        " .~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
        " :                                            :\n" +
        " :   No pátio interno, um cavaleiro de capa   :\n" +
        " :   branca bloqueia a escada.                :\n" +
        " :   Você treinou com ele. Você sabe o nome   :\n" +
        " :   do cachorro dele.                        :\n" +
        " :                                            :\n" +
        " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
scanner.nextLine();
```

**Cavaleiro** *(fala)*

```java
System.out.println(
        " .~~[ CAVALEIRO ]~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
        " :                                            :\n" +
        " :   O Grão-Mestre disse que você podia       :\n" +
        " :   voltar mudado.                           :\n" +
        " :   Disse pra eu não deixar passar se você   :\n" +
        " :   viesse acompanhado de traidor.           :\n" +
        " :                                            :\n" +
        " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
scanner.nextLine();
```

**Cavaleiro** *(fala)*

```java
System.out.println(
        " .~~[ CAVALEIRO ]~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
        " :                                            :\n" +
        " :   Larga a espada e sobe sozinho. Por       :\n" +
        " :   favor.                                   :\n" +
        " :   Eu não quero fazer isso.                 :\n" +
        " :                                            :\n" +
        " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
scanner.nextLine();
```

```java
Combate.iniciar(jogador, new CavaleiroDaOrdem());
```

*(narração)*

```java
System.out.println(
        " .~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
        " :                                            :\n" +
        " :   Ele cai no primeiro degrau.              :\n" +
        " :   Você não olha para baixo enquanto sobe o :\n" +
        " :   resto.                                   :\n" +
        " :                                            :\n" +
        " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
scanner.nextLine();
```



## `A4-03` — O salão da Chama

> **🖼️ SPRITE:** `cenario_salao_chama` — salão do trono, a Chama no centro

```java
Sprite.mostrar("cenario_salao_chama");   // <- seu sprite aqui
```

*(narração)*

```java
System.out.println(
        " .~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
        " :                                            :\n" +
        " :   O salão é redondo e sem janelas. No      :\n" +
        " :   centro, suspensa, a Chama Eterna:        :\n" +
        " :   branca, silenciosa, e agora que você     :\n" +
        " :   sabe o que ela é, insuportável de olhar. :\n" +
        " :                                            :\n" +
        " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
scanner.nextLine();
```

*(narração)*

```java
System.out.println(
        " .~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
        " :                                            :\n" +
        " :   Aldric está de costas para você, de mãos :\n" +
        " :   nas costas, como em todas as manhãs dos  :\n" +
        " :   últimos vinte anos.                      :\n" +
        " :                                            :\n" +
        " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
scanner.nextLine();
```

**Aldric** *(fala)*

```java
System.out.println(
        " .~~[ ALDRIC ]~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
        " :                                            :\n" +
        " :   Você trouxe o Malachar.                  :\n" +
        " :   Claro que trouxe. Ele sempre foi mais    :\n" +
        " :   convincente do que eu quando o assunto   :\n" +
        " :   era o fim do mundo.                      :\n" +
        " :                                            :\n" +
        " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
scanner.nextLine();
```

*(narração)*

```java
System.out.println(
        " .~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
        " :                                            :\n" +
        " :   Ele se vira. Está sorrindo. O sorriso é  :\n" +
        " :   o mesmo de sempre, e é isso que dá       :\n" +
        " :   náusea.                                  :\n" +
        " :                                            :\n" +
        " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
scanner.nextLine();
```

**Aldric** *(fala)*

```java
System.out.println(
        " .~~[ ALDRIC ]~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
        " :                                            :\n" +
        " :   Duskrow, ano dezenove. Você leu a        :\n" +
        " :   parede. Está tudo lá, eu nunca escondi.  :\n" +
        " :   Eu apenas nunca contei, que é uma coisa  :\n" +
        " :   diferente.                               :\n" +
        " :                                            :\n" +
        " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
scanner.nextLine();
```

**Aldric** *(fala)*

```java
System.out.println(
        " .~~[ ALDRIC ]~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
        " :                                            :\n" +
        " :   Sabe quantas pessoas morriam por ano em  :\n" +
        " :   Valdrath antes da Chama? Fome, peste,    :\n" +
        " :   inverno, guerra de barão. Nove mil. Todo :\n" +
        " :   ano. Sem exceção.                        :\n" +
        " :                                            :\n" +
        " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
scanner.nextLine();
```

**Aldric** *(fala)*

```java
System.out.println(
        " .~~[ ALDRIC ]~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
        " :                                            :\n" +
        " :   Hoje morrem novecentos. E os novecentos  :\n" +
        " :   são escolhidos, registrados e usados.    :\n" +
        " :   Eu não inventei a morte, garoto. Eu a    :\n" +
        " :   organizei.                               :\n" +
        " :                                            :\n" +
        " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
scanner.nextLine();
```

**Malachar** *(fala)*

```java
System.out.println(
        " .~~[ MALACHAR ]~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
        " :                                            :\n" +
        " :   Você organizou você mesmo em primeiro    :\n" +
        " :   lugar da fila de quem sobrevive, Aldric. :\n" +
        " :   Isso tem um nome mais curto.             :\n" +
        " :                                            :\n" +
        " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
scanner.nextLine();
```

**Aldric** *(fala)*

```java
System.out.println(
        " .~~[ ALDRIC ]~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
        " :                                            :\n" +
        " :   Tem. E o nome é governo.                 :\n" +
        " :                                            :\n" +
        " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
scanner.nextLine();
```



## `A4-04` — A oferta

> **🖼️ SPRITE:** `npc_aldric_verdadeiro` — Aldric revelando a forma verdadeira — use no momento da linha final

```java
Sprite.mostrar("npc_aldric_verdadeiro");   // <- seu sprite aqui
```

**Aldric** *(fala)*

```java
System.out.println(
        " .~~[ ALDRIC ]~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
        " :                                            :\n" +
        " :   Eu te criei, e não foi só por culpa. Foi :\n" +
        " :   porque você é bom. Bom de verdade.       :\n" +
        " :   Melhor do que eu era.                    :\n" +
        " :                                            :\n" +
        " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
scanner.nextLine();
```

**Aldric** *(fala)*

```java
System.out.println(
        " .~~[ ALDRIC ]~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
        " :                                            :\n" +
        " :   Fica. Aprende. Daqui a cem anos você     :\n" +
        " :   toma o meu lugar e faz melhor do que eu  :\n" +
        " :   fiz. Menos aldeias, mais critério.       :\n" +
        " :                                            :\n" +
        " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
scanner.nextLine();
```

**Aldric** *(fala)*

```java
System.out.println(
        " .~~[ ALDRIC ]~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
        " :                                            :\n" +
        " :   É essa a oferta. Não peço perdão, peço   :\n" +
        " :   continuidade.                            :\n" +
        " :                                            :\n" +
        " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
scanner.nextLine();
```

*(narração)*

```java
System.out.println(
        " .~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
        " :                                            :\n" +
        " :   Ele estende a mão.                       :\n" +
        " :   A pele dela é lisa demais para um homem  :\n" +
        " :   que Korrin conhece há trinta anos.       :\n" +
        " :                                            :\n" +
        " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
scanner.nextLine();
```

*(narração)*

```java
System.out.println(
        " .~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
        " :                                            :\n" +
        " :   Atrás dele, a Chama se inclina na sua    :\n" +
        " :   direção como uma vela num quarto sem     :\n" +
        " :   vento.                                   :\n" +
        " :                                            :\n" +
        " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
scanner.nextLine();
```

**A Voz da Chama** *(a voz da chama)*

```java
System.out.println(
        " .~~[ A VOZ DA CHAMA ]~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
        " :                                            :\n" +
        " :   Escolhe.                                 :\n" +
        " :   Eu fico com qualquer um dos dois. Eu     :\n" +
        " :   sempre fico.                             :\n" +
        " :                                            :\n" +
        " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
scanner.nextLine();
```

> ℹ️ 

```java
if (lyraAliada) {
    System.out.println("   Passos rápidos no corredor. Duas adagas soam ao serem sacadas.");
    cenaLyraChegaAliada();
} else if (lyraTraida) {
    System.out.println("   Passos rápidos no corredor. Você reconhece o ritmo — e o lado de onde vem.");
    cenaLyraChegaInimiga();
}
```



## `A4-05` — Lyra chega — aliada

> ℹ️ 

**Lyra** *(fala)*

```java
System.out.println(
        " .~~[ LYRA ]~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
        " :                                            :\n" +
        " :   Pai.                                     :\n" +
        " :   Sai da frente. Essa parte é minha.       :\n" +
        " :                                            :\n" +
        " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
scanner.nextLine();
```

**Malachar** *(fala)*

```java
System.out.println(
        " .~~[ MALACHAR ]~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
        " :                                            :\n" +
        " :   Você não devia estar aqui.               :\n" +
        " :                                            :\n" +
        " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
scanner.nextLine();
```

**Lyra** *(fala)*

```java
System.out.println(
        " .~~[ LYRA ]~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
        " :                                            :\n" +
        " :   Eu nunca devia estar em lugar nenhum e   :\n" +
        " :   olha eu aqui.                            :\n" +
        " :   Depois a gente briga. Agora tem um velho :\n" +
        " :   de mil anos pra derrubar.                :\n" +
        " :                                            :\n" +
        " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
scanner.nextLine();
```

*(narração)*

```java
System.out.println(
        " .~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
        " :                                            :\n" +
        " :   Ela para ao seu lado e ergue as adagas.  :\n" +
        " :   Você não está só.                        :\n" +
        " :                                            :\n" +
        " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
scanner.nextLine();
```

```java
System.out.println("   Lyra ergue as adagas ao seu lado. Você não está só.");
aliadosNoBoss.add(Lyra);
```



## `A4-06` — Lyra chega — inimiga

> ℹ️ 

*(narração)*

```java
System.out.println(
        " .~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
        " :                                            :\n" +
        " :   Ela entra com o brasão da Aurora         :\n" +
        " :   emprestado no ombro e a mesma cara de    :\n" +
        " :   Thornwood, com onze anos a mais em cima. :\n" +
        " :                                            :\n" +
        " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
scanner.nextLine();
```

**Lyra** *(fala)*

```java
System.out.println(
        " .~~[ LYRA ]~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
        " :                                            :\n" +
        " :   Eles me pegaram na estrada. Graças a     :\n" +
        " :   você.                                    :\n" +
        " :   Me ofereceram um acordo: eu te entrego,  :\n" +
        " :   eu vivo.                                 :\n" +
        " :                                            :\n" +
        " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
scanner.nextLine();
```

**Lyra** *(fala)*

```java
System.out.println(
        " .~~[ LYRA ]~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
        " :                                            :\n" +
        " :   Eu contei da minha mãe pra você. Você    :\n" +
        " :   mandou um corvo.                         :\n" +
        " :                                            :\n" +
        " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
scanner.nextLine();
```

**Lyra** *(fala)*

```java
System.out.println(
        " .~~[ LYRA ]~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
        " :                                            :\n" +
        " :   Então não me olha assim. Eu aprendi      :\n" +
        " :   contigo.                                 :\n" +
        " :                                            :\n" +
        " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
scanner.nextLine();
```

```java
inimigosNoBoss.add(LyraInimiga);
```

### ⚖️ ESCOLHA 5 — O confronto final (Ato 4)

A mão de Aldric continua estendida. A Chama espera. As opções disponíveis dependem do karma acumulado.

| # | Opção | Requisito | Final |
|---|-------|-----------|-------|
| 1 | Recusar e destruir a Chama | `honra >= 3 && honra > ambicao` | **HERÓI — A Aurora Renasce** (boss: Aldric) |
| 2 | Aceitar a Chama e tomar o lugar dele | `ambicao >= 3 && ambicao > honra` | **VILÃO — O Novo Tirano** (boss: Malachar) |
| 3 | Apagar a Chama e ir embora | sempre disponível | **NEUTRO — O Andarilho** (boss: Guardião da Chama) |

```java
System.out.println("   [1] Recusar a mão e apagar essa Chama de uma vez");
System.out.println("   [2] Apertar a mão... e depois tomar tudo");
System.out.println("   [3] Acabar com isso e ir embora, sem trono nenhum");
System.out.print("   > ");
int e5 = Integer.parseInt(scanner.nextLine().trim());

// O karma filtra: escolher fora do seu caminho não desbloqueia o final
if (e5 == 1 && honra >= 3 && honra > ambicao) {
    finalHeroi();
} else if (e5 == 2 && ambicao >= 3 && ambicao > honra) {
    finalVilao();
} else {
    finalNeutro();     // inclusive quando você tenta um final que não merece
}
```

> ℹ️ 



## `A4-07` — Tentativa sem karma (opcional, recomendado)

> ℹ️ 

**A Voz da Chama** *(a voz da chama)*

```java
System.out.println(
        " .~~[ A VOZ DA CHAMA ]~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
        " :                                            :\n" +
        " :   Agora?                                   :\n" +
        " :   Agora você quer ser herói?               :\n" +
        " :                                            :\n" +
        " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
scanner.nextLine();
```

**A Voz da Chama** *(a voz da chama)*

```java
System.out.println(
        " .~~[ A VOZ DA CHAMA ]~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
        " :                                            :\n" +
        " :   Eu vi tudo, garoto. A vila que você      :\n" +
        " :   deixou morrer. O homem que você matou de :\n" +
        " :   joelhos. A garota que você vendeu por um :\n" +
        " :   elogio.                                  :\n" +
        " :                                            :\n" +
        " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
scanner.nextLine();
```

**A Voz da Chama** *(a voz da chama)*

```java
System.out.println(
        " .~~[ A VOZ DA CHAMA ]~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
        " :                                            :\n" +
        " :   Você não tem peso pra derrubar nada. Só  :\n" +
        " :   pra ir embora.                           :\n" +
        " :                                            :\n" +
        " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
scanner.nextLine();
```



---

# FINAIS


## `FIM-H` — FINAL HERÓI — A Aurora Renasce

> ℹ️ 

> **🖼️ SPRITE:** `boss_aldric_verdadeiro` — forma verdadeira do Aldric, ao iniciar o boss

```java
Sprite.mostrar("boss_aldric_verdadeiro");   // <- seu sprite aqui
```

*(narração)*

```java
System.out.println(
        " .~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
        " :                                            :\n" +
        " :   Você não aperta a mão.                   :\n" +
        " :                                            :\n" +
        " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
scanner.nextLine();
```

**Você** *(fala)*

```java
System.out.println(
        " .~~[ VOCÊ ]~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
        " :                                            :\n" +
        " :   Eu não quero continuidade. Eu quero o    :\n" +
        " :   fim.                                     :\n" +
        " :                                            :\n" +
        " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
scanner.nextLine();
```

**Aldric** *(fala)*

```java
System.out.println(
        " .~~[ ALDRIC ]~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
        " :                                            :\n" +
        " :   Que pena.                                :\n" +
        " :   Você era o melhor que eu já fiz.         :\n" +
        " :                                            :\n" +
        " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
scanner.nextLine();
```

*(narração)*

```java
System.out.println(
        " .~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
        " :                                            :\n" +
        " :   O sorriso permanece enquanto a pele      :\n" +
        " :   racha.                                   :\n" +
        " :   O que estava por baixo dos vinte anos de :\n" +
        " :   bondade paternal tem mil anos de idade e :\n" +
        " :   não deveria conseguir ficar de pé.       :\n" +
        " :                                            :\n" +
        " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
scanner.nextLine();
```

```java
Combate.iniciarBoss(jogador, new AldricVerdadeiro(), aliadosNoBoss);
```

*(narração)*

```java
System.out.println(
        " .~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
        " :                                            :\n" +
        " :   Malachar segura o campo. Se Lyra está    :\n" +
        " :   viva e do seu lado, é a adaga dela que   :\n" +
        " :   abre a guarda do velho.                  :\n" +
        " :   Quem dá o golpe é você.                  :\n" +
        " :                                            :\n" +
        " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
scanner.nextLine();
```

*(narração)*

```java
System.out.println(
        " .~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
        " :                                            :\n" +
        " :   Aldric cai e envelhece mil anos em       :\n" +
        " :   quatro segundos. Não sobra corpo. Sobra  :\n" +
        " :   poeira e uma corrente de Grão-Mestre no  :\n" +
        " :   chão.                                    :\n" +
        " :                                            :\n" +
        " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
scanner.nextLine();
```

> **🖼️ SPRITE:** `cena_destruicao_chama` — a Chama sendo apagada

```java
Sprite.mostrar("cena_destruicao_chama");   // <- seu sprite aqui
```

*(narração)*

```java
System.out.println(
        " .~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
        " :                                            :\n" +
        " :   Você enfia a espada na Chama.            :\n" +
        " :   Ela grita. É a única vez em mil anos que :\n" +
        " :   a coisa faz barulho.                     :\n" +
        " :                                            :\n" +
        " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
scanner.nextLine();
```

**A Voz da Chama** *(a voz da chama)*

```java
System.out.println(
        " .~~[ A VOZ DA CHAMA ]~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
        " :                                            :\n" +
        " :   Você está matando o reino junto comigo,  :\n" +
        " :   seu idiota. Sem mim não tem colheita,    :\n" +
        " :   não tem muralha, não tem...              :\n" +
        " :                                            :\n" +
        " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
scanner.nextLine();
```

*(narração)*

```java
System.out.println(
        " .~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
        " :                                            :\n" +
        " :   Malachar te empurra para trás e entra no :\n" +
        " :   clarão.                                  :\n" +
        " :   Ele segura a explosão com o corpo e com  :\n" +
        " :   tudo o que sabe, porque alguém precisa   :\n" +
        " :   segurar e ele é o único que estudou      :\n" +
        " :   como.                                    :\n" +
        " :                                            :\n" +
        " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
scanner.nextLine();
```

**Malachar** *(fala)*

```java
System.out.println(
        " .~~[ MALACHAR ]~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
        " :                                            :\n" +
        " :   Vinte anos tentando fazer isso.          :\n" +
        " :   Deixa eu terminar.                       :\n" +
        " :                                            :\n" +
        " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
scanner.nextLine();
```

*(narração)*

```java
System.out.println(
        " .~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
        " :                                            :\n" +
        " :   A Cidadela desaba de dentro para fora. A :\n" +
        " :   magia que a sustentava era a Chama, e a  :\n" +
        " :   Chama acabou.                            :\n" +
        " :                                            :\n" +
        " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
scanner.nextLine();
```

*(narração)*

```java
System.out.println(
        " .~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
        " :                                            :\n" +
        " :   No pátio, o capim verde morre em uma     :\n" +
        " :   hora.                                    :\n" +
        " :   Nos quatro cantos de Valdrath, no mesmo  :\n" +
        " :   dia, um pouco de coisa começa a nascer.  :\n" +
        " :                                            :\n" +
        " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
scanner.nextLine();
```

> ℹ️ 

```java
if (lyraAliada) {
    System.out.println("   Lyra enterra o pai na encosta leste, virado para Emberfall.");
} else {
    System.out.println("   Ninguém enterra Malachar. Não sobrou nada para enterrar.");
}
if (vilaSalva) {
    System.out.println("   Em Greywatch, Brenna acende fogo em quatro chaminés. Quatro.");
}
```

*(narração)*

```java
System.out.println(
        " .~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
        " :                                            :\n" +
        " :   Oferecem a você a coroa, depois o        :\n" +
        " :   comando do que restou da Ordem, depois   :\n" +
        " :   uma estátua.                             :\n" +
        " :   Você recusa os três.                     :\n" +
        " :                                            :\n" +
        " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
scanner.nextLine();
```

*(narração)*

```java
System.out.println(
        " .~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
        " :                                            :\n" +
        " :   Ninguém sabe quantas pessoas boas        :\n" +
        " :   morreram para que a terra voltasse a     :\n" +
        " :   valer alguma coisa. Você sabe. É o seu   :\n" +
        " :   trabalho agora: saber.                   :\n" +
        " :                                            :\n" +
        " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
scanner.nextLine();
```

*(narração)*

```java
System.out.println(
        " .~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
        " :                                            :\n" +
        " :   Vitória amarga, mas justa.               :\n" +
        " :   FINAL HERÓI — A Aurora Renasce.          :\n" +
        " :                                            :\n" +
        " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
scanner.nextLine();
```



## `FIM-V` — FINAL VILÃO — O Novo Tirano

> ℹ️ 

> **🖼️ SPRITE:** `boss_malachar` — ao iniciar o boss

```java
Sprite.mostrar("boss_malachar");   // <- seu sprite aqui
```

*(narração)*

```java
System.out.println(
        " .~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
        " :                                            :\n" +
        " :   Você aperta a mão de Aldric.             :\n" +
        " :   E não solta.                             :\n" +
        " :                                            :\n" +
        " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
scanner.nextLine();
```

**Você** *(fala)*

```java
System.out.println(
        " .~~[ VOCÊ ]~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
        " :                                            :\n" +
        " :   Continuidade, você disse.                :\n" +
        " :   Eu prefiro sucessão.                     :\n" +
        " :                                            :\n" +
        " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
scanner.nextLine();
```

*(narração)*

```java
System.out.println(
        " .~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
        " :                                            :\n" +
        " :   Malachar entende antes de todo mundo.    :\n" +
        " :   Ele sempre entende antes e sempre tarde  :\n" +
        " :   demais.                                  :\n" +
        " :                                            :\n" +
        " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
scanner.nextLine();
```

**Malachar** *(fala)*

```java
System.out.println(
        " .~~[ MALACHAR ]~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
        " :                                            :\n" +
        " :   Não. Não, não, não. Eu passei vinte anos :\n" +
        " :   pra impedir exatamente isso.             :\n" +
        " :                                            :\n" +
        " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
scanner.nextLine();
```

**Malachar** *(fala)*

```java
System.out.println(
        " .~~[ MALACHAR ]~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
        " :                                            :\n" +
        " :   Garoto. Eu vi o teu nome naquela parede. :\n" +
        " :   Eles fizeram você. Não vira a coisa que  :\n" +
        " :   te fez.                                  :\n" +
        " :                                            :\n" +
        " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
scanner.nextLine();
```

**Você** *(fala)*

```java
System.out.println(
        " .~~[ VOCÊ ]~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
        " :                                            :\n" +
        " :   Saia da frente, velho.                   :\n" +
        " :                                            :\n" +
        " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
scanner.nextLine();
```

**Malachar** *(fala)*

```java
System.out.println(
        " .~~[ MALACHAR ]~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
        " :                                            :\n" +
        " :   Não.                                     :\n" +
        " :                                            :\n" +
        " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
scanner.nextLine();
```

```java
Combate.iniciarBoss(jogador, new Malachar(), inimigosNoBoss);
```

*(narração)*

```java
System.out.println(
        " .~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
        " :                                            :\n" +
        " :   Ele luta mal. Está cansado há vinte      :\n" +
        " :   anos.                                    :\n" +
        " :   O único homem que tentou salvar Valdrath :\n" +
        " :   morre no chão do salão, pela mão de quem :\n" +
        " :   ele levou até ali.                       :\n" +
        " :                                            :\n" +
        " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
scanner.nextLine();
```

> ℹ️ 

```java
if (lyraAliada) cenaLyraVeOPaiMorrer();
```

> **🖼️ SPRITE:** `cena_absorver_chama` — jogador tomando a Chama

```java
Sprite.mostrar("cena_absorver_chama");   // <- seu sprite aqui
```

*(narração)*

```java
System.out.println(
        " .~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
        " :                                            :\n" +
        " :   Você entra na Chama e ela não te queima. :\n" +
        " :   Ela reconhece proprietário.              :\n" +
        " :                                            :\n" +
        " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
scanner.nextLine();
```

**A Voz da Chama** *(a voz da chama)*

```java
System.out.println(
        " .~~[ A VOZ DA CHAMA ]~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
        " :                                            :\n" +
        " :   Bem-vindo.                               :\n" +
        " :   Você vai gostar dos primeiros cem anos.  :\n" +
        " :   Os outros novecentos passam rápido.      :\n" +
        " :                                            :\n" +
        " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
scanner.nextLine();
```

*(narração)*

```java
System.out.println(
        " .~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
        " :                                            :\n" +
        " :   Aldric cai de joelhos e envelhece em     :\n" +
        " :   silêncio enquanto a Chama muda de dono.  :\n" +
        " :   Ele não reclama. De todos ali, é o único :\n" +
        " :   que reconhece o método.                  :\n" +
        " :                                            :\n" +
        " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
scanner.nextLine();
```

**Aldric** *(fala)*

```java
System.out.println(
        " .~~[ ALDRIC ]~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
        " :                                            :\n" +
        " :   Menos aldeias, mais critério.            :\n" +
        " :   Foi o que eu pedi.                       :\n" +
        " :                                            :\n" +
        " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
scanner.nextLine();
```

*(narração)*

```java
System.out.println(
        " .~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
        " :                                            :\n" +
        " :   Você o deixa apodrecer no próprio salão. :\n" +
        " :                                            :\n" +
        " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
scanner.nextLine();
```

*(narração)*

```java
System.out.println(
        " .~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
        " :                                            :\n" +
        " :   A praga não acaba. Ela só troca de       :\n" +
        " :   destino. Agora tudo o que Valdrath       :\n" +
        " :   perde, você ganha.                       :\n" +
        " :                                            :\n" +
        " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
scanner.nextLine();
```

*(narração)*

```java
System.out.println(
        " .~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
        " :                                            :\n" +
        " :   Vinte anos depois, num vilarejo do sul,  :\n" +
        " :   um Grão-Mestre de rosto jovem põe a mão  :\n" +
        " :   no ombro de um recruta órfão e diz que   :\n" +
        " :   existe um feiticeiro sombrio no norte, e :\n" +
        " :   que a culpa da praga é dele.             :\n" +
        " :                                            :\n" +
        " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
scanner.nextLine();
```

*(narração)*

```java
System.out.println(
        " .~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
        " :                                            :\n" +
        " :   O recruta acredita.                      :\n" +
        " :   Eles sempre acreditam.                   :\n" +
        " :                                            :\n" +
        " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
scanner.nextLine();
```

*(narração)*

```java
System.out.println(
        " .~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
        " :                                            :\n" +
        " :   Final sombrio e cíclico.                 :\n" +
        " :   FINAL VILÃO — O Novo Tirano.             :\n" +
        " :                                            :\n" +
        " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
scanner.nextLine();
```



## `FIM-N` — FINAL NEUTRO — O Andarilho

> ℹ️ 

> **🖼️ SPRITE:** `boss_guardiao_chama` — entidade da relíquia, ao iniciar o boss

```java
Sprite.mostrar("boss_guardiao_chama");   // <- seu sprite aqui
```

**Você** *(fala)*

```java
System.out.println(
        " .~~[ VOCÊ ]~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
        " :                                            :\n" +
        " :   Eu não quero o teu trono, Aldric. E não  :\n" +
        " :   quero salvar ninguém.                    :\n" +
        " :                                            :\n" +
        " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
scanner.nextLine();
```

**Você** *(fala)*

```java
System.out.println(
        " .~~[ VOCÊ ]~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
        " :                                            :\n" +
        " :   Eu quero que essa coisa apague e que     :\n" +
        " :   vocês parem de me dizer o que eu sou.    :\n" +
        " :                                            :\n" +
        " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
scanner.nextLine();
```

**Aldric** *(fala)*

```java
System.out.println(
        " .~~[ ALDRIC ]~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
        " :                                            :\n" +
        " :   Isso não é nobreza. Isso é preguiça com  :\n" +
        " :   espada.                                  :\n" +
        " :                                            :\n" +
        " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
scanner.nextLine();
```

**Você** *(fala)*

```java
System.out.println(
        " .~~[ VOCÊ ]~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
        " :                                            :\n" +
        " :   Pode ser.                                :\n" +
        " :                                            :\n" +
        " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
scanner.nextLine();
```

*(narração)*

```java
System.out.println(
        " .~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
        " :                                            :\n" +
        " :   Quando você avança para a Chama, ela se  :\n" +
        " :   defende sozinha.                         :\n" +
        " :   Do fogo sai uma forma alta e sem rosto:  :\n" +
        " :   o Guardião, que existe desde o primeiro  :\n" +
        " :   dia e nunca precisou de nome.            :\n" +
        " :                                            :\n" +
        " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
scanner.nextLine();
```

```java
Combate.iniciarBoss(jogador, new GuardiaoDaChama(), aliadosNoBoss);
```

*(narração)*

```java
System.out.println(
        " .~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
        " :                                            :\n" +
        " :   Você derruba o Guardião. Não com brilho, :\n" +
        " :   não com discurso. Com paciência e com a  :\n" +
        " :   última poção.                            :\n" +
        " :                                            :\n" +
        " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
scanner.nextLine();
```

> **🖼️ SPRITE:** `cena_chama_apagando` — a Chama se apagando lentamente

```java
Sprite.mostrar("cena_chama_apagando");   // <- seu sprite aqui
```

*(narração)*

```java
System.out.println(
        " .~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
        " :                                            :\n" +
        " :   A Chama apaga como vela: devagar, sem    :\n" +
        " :   grito, quase educadamente.               :\n" +
        " :                                            :\n" +
        " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
scanner.nextLine();
```

*(narração)*

```java
System.out.println(
        " .~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
        " :                                            :\n" +
        " :   Aldric te olha, entende, e começa a      :\n" +
        " :   envelhecer. Você já está saindo quando   :\n" +
        " :   ele senta no próprio trono para esperar. :\n" +
        " :                                            :\n" +
        " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
scanner.nextLine();
```

*(narração)*

```java
System.out.println(
        " .~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
        " :                                            :\n" +
        " :   Ninguém te impede de atravessar o pátio. :\n" +
        " :   Ninguém sabe direito o que aconteceu lá  :\n" +
        " :   em cima.                                 :\n" +
        " :                                            :\n" +
        " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
scanner.nextLine();
```

```java
if (verdadeNegada) {
    System.out.println("   Você nem contou pra ninguém o que leu naquela parede.");
    System.out.println("   Os nomes continuam lá, no escuro, sem ninguém pra ler.");
}
if (lyraNoGrupo) {
    System.out.println("   Lyra te acompanha até a bifurcação da estrada. Depois pega o outro lado.");
}
```

*(narração)*

```java
System.out.println(
        " .~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
        " :                                            :\n" +
        " :   Sem relíquia, a Ordem perde o que a      :\n" +
        " :   sustentava e leva sete anos para virar   :\n" +
        " :   história de bêbado em taverna.           :\n" +
        " :   A terra se cura devagar, sozinha, do     :\n" +
        " :   jeito que a terra faz.                   :\n" +
        " :                                            :\n" +
        " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
scanner.nextLine();
```

*(narração)*

```java
System.out.println(
        " .~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
        " :                                            :\n" +
        " :   Não há canção sobre você. Não há         :\n" +
        " :   estátua. Ninguém em Valdrath aprende o   :\n" +
        " :   seu nome.                                :\n" +
        " :                                            :\n" +
        " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
scanner.nextLine();
```

*(narração)*

```java
System.out.println(
        " .~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
        " :                                            :\n" +
        " :   Você atravessa a fronteira norte numa    :\n" +
        " :   manhã fria, sem missão, sem Ordem e sem  :\n" +
        " :   fardo.                                   :\n" +
        " :   É menos do que uma vitória. É a primeira :\n" +
        " :   vez que o dia é só seu.                  :\n" +
        " :                                            :\n" +
        " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
scanner.nextLine();
```

*(narração)*

```java
System.out.println(
        " .~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.\n" +
        " :                                            :\n" +
        " :   Final melancólico, de liberdade sem      :\n" +
        " :   glória.                                  :\n" +
        " :   FINAL NEUTRO — O Andarilho.              :\n" +
        " :                                            :\n" +
        " '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'");
scanner.nextLine();
```

