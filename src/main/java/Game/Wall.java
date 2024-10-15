package Game;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;

import java.io.IOException;

public class Wall extends Element {

    public Wall(int x, int y) {
        super(x, y); // Chama o construtor da classe Element
    }

    @Override
    public void draw(TextGraphics graphics) throws IOException {
        graphics.setForegroundColor(TextColor.Factory.fromString("#FF0000")); // Cor do muro
        graphics.putString(new TerminalPosition(position.getX() * 2, position.getY() * 2), "\\/");
        graphics.putString(new TerminalPosition(position.getX() * 2, position.getY() * 2 + 1), "/\\");
    }
}
