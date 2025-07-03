# Jogo da Forca em Java

Este projeto é uma implementação do clássico jogo da forca em Java, com estrutura modular e interface em console. O jogo permite que o usuário escolha entre jogar com uma palavra aleatória ou digitar uma palavra personalizada para adivinhar.

---

## Funcionalidades

- Escolha entre palavra aleatória ou personalizada
- Exibição gráfica simplificada da forca em ASCII
- Controle de letras corretas e erradas
- Contagem de tentativas restantes
- Mensagens de feedback para o jogador
- Registro de estatísticas: número total de jogos, vitórias e derrotas
- Opção de jogar várias vezes com menu interativo
- Validação de entradas para evitar erros

---

## Estrutura do Código

- **PalavraSecreta**: Gerencia a palavra oculta e verifica as letras acertadas.
- **Forca**: Controla o estado do jogo, tentativas e desenho da forca.
- **ForcaUI**: Responsável pela interação com o usuário via console.
- **JogoDaForcaModular**: Classe principal que inicializa o jogo e gerencia o fluxo de partidas.

---

## Como executar

### Requisitos

- Java JDK 8 ou superior instalado
- Terminal ou prompt de comando

### Passos

1. Salve o arquivo `JogoDaForcaModular.java` em uma pasta de sua preferência.
2. Abra o terminal ou prompt de comando e navegue até essa pasta.
3. Compile o código:

   ```bash
   javac JogoDaForcaModular.java
