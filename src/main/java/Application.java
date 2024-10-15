import Game.Game;

import java.io.IOException;

public class Application {
    public static void main(String[] args) {
        try {
            Game game = new Game(); // Cria uma nova instância do Game.Game
            game.run(); // Chama o método run
        } catch (IOException e) {
            e.printStackTrace(); // Lida com exceções
        }
    }
}
