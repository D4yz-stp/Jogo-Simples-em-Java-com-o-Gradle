package Game;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import java.io.IOException;

public class Coin extends Element {
    public Coin(int x, int y) {
        super(x, y); // Chama o construtor da classe Element
    }

    @Override
    public void draw(TextGraphics graphics) throws IOException {
        graphics.setForegroundColor(TextColor.Factory.fromString("#FFD700")); // Cor dourada para a moeda
        graphics.putString(new TerminalPosition(getPosition().getX(), getPosition().getY()), "O"); // Representação da moeda
    }
}
