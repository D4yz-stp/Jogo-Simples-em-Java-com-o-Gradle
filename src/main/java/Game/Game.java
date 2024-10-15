package Game;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.terminal.Terminal;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.graphics.TextGraphics;

import java.io.IOException;


public class Game {
    private Screen screen;
    private Hero hero;
    private Arena arena;

    public Game() throws IOException {
        Terminal terminal = new DefaultTerminalFactory().createTerminal();
        screen = new TerminalScreen(terminal);
        screen.setCursorPosition(null); // Não precisamos de um cursor
        screen.startScreen(); // Iniciar a tela
        screen.doResizeIfNecessary(); // Redimensionar se necessário

        // Inicializa o herói
        hero = new Hero(10, 10);
        arena = new Arena(screen.getTerminalSize().getColumns(), screen.getTerminalSize().getRows(), hero);
    }

    private void processKey(KeyStroke key) {
        arena.processKey(key);
    }

    private void draw() throws IOException {
        screen.clear(); // Limpa a tela
        arena.draw(screen.newTextGraphics()); // Desenha o herói
        screen.refresh(); // Atualiza a tela
    }

    public void run() throws IOException {
        boolean running = true;
        try {
            while (running) {
                draw(); // Chama o método de desenhar
                KeyStroke key = screen.readInput();

                // Verifica se a tecla 'q' foi pressionada
                if (key.getKeyType() == KeyType.Character && key.getCharacter() == 'q') {
                    running = false;
                } else {
                    processKey(key); // Processa outras teclas
                }
            }
        }finally {
            // Fecha a tela corretamente quando o loop termina
            screen.close();
        }
        System.out.println("Jogo Encerrado!");
    }
}
