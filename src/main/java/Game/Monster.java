package Game;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import java.io.IOException;
import java.util.Random;

public class Monster extends Element {
    public Monster(int x, int y) {
        super(x, y); // Chama o construtor da classe Element
    }

    @Override
    public void draw(TextGraphics graphics) throws IOException {
        graphics.setForegroundColor(TextColor.Factory.fromString("#FF0000")); // Cor vermelha para o monstro
        graphics.putString(new TerminalPosition(getPosition().getX(), getPosition().getY()), "M"); // Representação do monstro
    }

    public Position move() {
        Random random = new Random();
        // Move o monstro para uma posição adjacente (norte, sul, leste ou oeste)
        int moveDirection = random.nextInt(4); // 0: cima, 1: baixo, 2: direita, 3: esquerda
        switch (moveDirection) {
            case 0: return new Position(getPosition().getX(), getPosition().getY() - 1); // Mover para cima
            case 1: return new Position(getPosition().getX(), getPosition().getY() + 1); // Mover para baixo
            case 2: return new Position(getPosition().getX() + 1, getPosition().getY()); // Mover para direita
            case 3: return new Position(getPosition().getX() - 1, getPosition().getY()); // Mover para esquerda
            default: return getPosition(); // Não se move se não for nenhum dos casos
        }
    }
}
