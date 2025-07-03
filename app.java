import java.util.*;

class PalavraSecreta {
    private final String palavra;
    private final Set<Character> letrasAcertadas;

    public PalavraSecreta(String palavra) {
        this.palavra = palavra.toLowerCase();
        this.letrasAcertadas = new HashSet<>();
    }

    public boolean tentarLetra(char letra) {
        letra = Character.toLowerCase(letra);
        if (palavra.indexOf(letra) >= 0) {
            letrasAcertadas.add(letra);
            return true;
        }
        return false;
    }

    public boolean jaFoiTentada(char letra) {
        letra = Character.toLowerCase(letra);
        return letrasAcertadas.contains(letra);
    }

    public boolean venceu() {
        for (char c : palavra.toCharArray()) {
            if (!letrasAcertadas.contains(c)) {
                return false;
            }
        }
        return true;
    }

    public String exibirPalavra() {
        StringBuilder sb = new StringBuilder();
        for (char c : palavra.toCharArray()) {
            if (letrasAcertadas.contains(c)) {
                sb.append(c).append(' ');
            } else {
                sb.append("_ ");
            }
        }
        return sb.toString();
    }

    public String getPalavra() {
        return palavra;
    }
}

class Forca {
    private static final String[] FORCA = {
        "  +---+\n  |   |\n      |\n      |\n      |\n      |\n=========\n",
        "  +---+\n  |   |\n  O   |\n      |\n      |\n      |\n=========\n",
        "  +---+\n  |   |\n  O   |\n  |   |\n      |\n      |\n=========\n",
        "  +---+\n  |   |\n  O   |\n /|   |\n      |\n      |\n=========\n",
        "  +---+\n  |   |\n  O   |\n /|\\  |\n      |\n      |\n=========\n",
        "  +---+\n  |   |\n  O   |\n /|\\  |\n /    |\n      |\n=========\n",
        "  +---+\n  |   |\n  O   |\n /|\\  |\n / \\  |\n      |\n=========\n"
    };

    private final PalavraSecreta palavraSecreta;
    private final Set<Character> letrasErradas;
    private int erros;

    private static final int MAX_ERROS = FORCA.length - 1;

    public Forca(PalavraSecreta palavraSecreta) {
        this.palavraSecreta = palavraSecreta;
        this.letrasErradas = new HashSet<>();
        this.erros = 0;
    }

    public boolean tentarLetra(char letra) {
        letra = Character.toLowerCase(letra);

        if (palavraSecreta.tentarLetra(letra)) {
            return true;
        } else {
            if (!letrasErradas.contains(letra)) {
                letrasErradas.add(letra);
                erros++;
            }
            return false;
        }
    }

    public boolean jaFoiTentada(char letra) {
        letra = Character.toLowerCase(letra);
        return palavraSecreta.jaFoiTentada(letra) || letrasErradas.contains(letra);
    }

    public boolean venceu() {
        return palavraSecreta.venceu();
    }

    public boolean perdeu() {
        return erros >= MAX_ERROS;
    }

    public String exibirEstado() {
        StringBuilder sb = new StringBuilder();
        sb.append(FORCA[erros]).append("\n");
        sb.append("Palavra: ").append(palavraSecreta.exibirPalavra()).append("\n");
        sb.append("Letras erradas: ").append(letrasErradas).append("\n");
        sb.append("Tentativas restantes: ").append(MAX_ERROS - erros).append("\n");
        return sb.toString();
    }

    public String getPalavra() {
        return palavraSecreta.getPalavra();
    }
}

class ForcaUI {
    private final Scanner scanner = new Scanner(System.in);

    public void mostrarMensagem(String mensagem) {
        System.out.println(mensagem);
    }

    public String pedirEntrada(String mensagem) {
        System.out.print(mensagem);
        return scanner.nextLine().trim();
    }

    public char pedirLetra() {
        while (true) {
            System.out.print("Digite uma letra: ");
            String entrada = scanner.nextLine().toLowerCase();
            if (entrada.length() == 1 && Character.isLetter(entrada.charAt(0))) {
                return entrada.charAt(0);
            }
            System.out.println("Entrada inválida. Por favor, digite uma única letra.");
        }
    }

    public boolean perguntarJogarNovamente() {
        while (true) {
            String resposta = pedirEntrada("Quer jogar novamente? (S/N): ").toLowerCase();
            if (resposta.equals("s")) {
                return true;
            } else if (resposta.equals("n")) {
                return false;
            } else {
                System.out.println("Resposta inválida. Digite 'S' para sim ou 'N' para não.");
            }
        }
    }

    public boolean escolherModoPalavra() {
        while (true) {
            String resp = pedirEntrada("Deseja (1) palavra aleatória ou (2) digitar a palavra? ");
            if (resp.equals("1")) {
                return false;
            } else if (resp.equals("2")) {
                return true;
            } else {
                System.out.println("Opção inválida. Digite 1 ou 2.");
            }
        }
    }

    public String lerPalavraPersonalizada() {
        while (true) {
            String palavra = pedirEntrada("Digite a palavra secreta (somente letras, sem espaços): ").toLowerCase();
            if (palavra.matches("[a-zA-Z]+")) {
                return palavra;
            }
            System.out.println("Palavra inválida. Use apenas letras e sem espaços.");
        }
    }
}

public class JogoDaForcaModular {
    private static final String[] PALAVRAS = {
        "computador", "programacao", "java", "teclado", "mouse",
        "monitor", "internet", "processador", "memoria", "arquivo"
    };

    public static void main(String[] args) {
        ForcaUI ui = new ForcaUI();
        Random random = new Random();

        int totalJogos = 0;
        int vitorias = 0;
        int derrotas = 0;

        ui.mostrarMensagem("=== Jogo da Forca ===");

        boolean jogar = true;

        while (jogar) {
            boolean palavraPersonalizada = ui.escolherModoPalavra();
            String palavra;

            if (palavraPersonalizada) {
                palavra = ui.lerPalavraPersonalizada();
                // Limpar tela para esconder palavra?
                System.out.print("\033[H\033[2J");  
                System.out.flush();
            } else {
                palavra = PALAVRAS[random.nextInt(PALAVRAS.length)];
            }

            PalavraSecreta palavraSecreta = new PalavraSecreta(palavra);
            Forca jogo = new Forca(palavraSecreta);

            boolean terminou = false;

            while (!terminou) {
                ui.mostrarMensagem(jogo.exibirEstado());

                char letra = ui.pedirLetra();

                if (jogo.jaFoiTentada(letra)) {
                    ui.mostrarMensagem("Letra já tentada. Tente outra.");
                    continue;
                }

                boolean correta = jogo.tentarLetra(letra);

                if (correta) {
                    ui.mostrarMensagem("Boa! Letra correta.");
                } else {
                    ui.mostrarMensagem("Letra incorreta.");
                }

                if (jogo.venceu()) {
                    ui.mostrarMensagem("\nParabéns! Você venceu! A palavra era: " + jogo.getPalavra());
                    vitorias++;
                    terminou = true;
                } else if (jogo.perdeu()) {
                    ui.mostrarMensagem(jogo.exibirEstado());
                    ui.mostrarMensagem("\nVocê perdeu! A palavra era: " + jogo.getPalavra());
                    derrotas++;
                    terminou = true;
                }
            }

            totalJogos++;
            ui.mostrarMensagem("\nJogos: " + totalJogos + " | Vitórias: " + vitorias + " | Derrotas: " + derrotas);

            jogar = ui.perguntarJogarNovamente();
        }

        ui.mostrarMensagem("Obrigado por jogar! Até a próxima.");
    }
}
