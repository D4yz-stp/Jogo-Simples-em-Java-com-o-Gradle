package Game;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.graphics.TextGraphics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Arena {
    private int width;
    private int height;
    private Hero hero;
    private List<Element> elements; // Lista de Elementos (Herói, Muros, Moedas, Monstros)
    private List<Coin> coins; // Lista de moedas
    private List<Monster> monsters; // Lista de monstros

    public Arena(int width, int height, Hero hero) {
        this.width = width;
        this.height = height;
        this.hero = hero;
        this.elements = createElements(); // Inicializa os elementos (muros e herói)
        this.coins = createCoins(); // Inicializa as moedas
        this.monsters = createMonsters(); // Inicializa os monstros
    }

    public void processKey(KeyStroke key) {
        Position newPosition = null;
        if (key.getKeyType() == KeyType.ArrowUp) {
            newPosition = hero.moveUp();
        } else if (key.getKeyType() == KeyType.ArrowDown) {
            newPosition = hero.moveDown();
        } else if (key.getKeyType() == KeyType.ArrowRight) {
            newPosition = hero.moveRight();
        } else if (key.getKeyType() == KeyType.ArrowLeft) {
            newPosition = hero.moveLeft();
        }

        if (newPosition != null) {
            moveHero(newPosition);
            retrieveCoins(); // Verifica se o herói pegou uma moeda
            moveMonsters(); // Move os monstros
            verifyMonsterCollisions(); // Verifica se o herói colidiu com um monstro
        }
    }

    public void draw(TextGraphics graphics) throws IOException {
        graphics.setBackgroundColor(TextColor.Factory.fromString("#336699"));
        graphics.fillRectangle(new TerminalPosition(0, 0), new TerminalSize(width, height), ' ');

        // Desenha os elementos (muros, herói, moedas, monstros)
        for (Element element : elements) {
            element.draw(graphics);
        }
        for (Coin coin : coins) {
            coin.draw(graphics); // Desenha as moedas
        }
        for (Monster monster : monsters) {
            monster.draw(graphics); // Desenha os monstros
        }
    }

    private boolean canHeroMove(Position position) {
        // Verifica se o herói está colidindo com algum elemento (muros ou moedas)
        for (Element element : elements) {
            if (element instanceof Wall && element.getPosition().equals(position)) {
                return false; // Não pode mover para a posição ocupada por um muro
            }
        }

        return position.getX() >= 0 && position.getX() < width && position.getY() >= 0 && position.getY() < height;
    }

    private void moveHero(Position newPosition) {
        if (canHeroMove(newPosition)) {
            hero.setPosition(newPosition);
        }
    }

    private List<Element> createElements() {
        List<Element> elements = new ArrayList<>();

        // Adiciona os muros nas extremidades
        for (int c = 0; c < width; c++) {
            elements.add(new Wall(c, 0)); // Muro superior
            elements.add(new Wall(c, height - 1)); // Muro inferior
        }
        for (int r = 1; r < height - 1; r++) {
            elements.add(new Wall(0, r)); // Muro esquerdo
            elements.add(new Wall(width - 1, r)); // Muro direito
        }
        elements.add(hero); // Adiciona o herói à lista de elementos
        return elements;
    }

    private List<Coin> createCoins() {
        Random random = new Random();
        List<Coin> coins = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            // Gera moedas em posições aleatórias que não estejam ocupadas por muros ou pelo herói
            int x, y;
            do {
                x = random.nextInt(width - 2) + 1;
                y = random.nextInt(height - 2) + 1;
            } while (!isPositionAvailable(x, y)); // Verifica se a posição está disponível
            coins.add(new Coin(x, y));
        }
        return coins;
    }

    private List<Monster> createMonsters() {
        Random random = new Random();
        List<Monster> monsters = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            // Gera monstros em posições aleatórias que não estejam ocupadas por muros ou pelo herói
            int x, y;
            do {
                x = random.nextInt(width - 2) + 1;
                y = random.nextInt(height - 2) + 1;
            } while (!isPositionAvailable(x, y)); // Verifica se a posição está disponível
            monsters.add(new Monster(x, y));
        }
        return monsters;
    }

    private boolean isPositionAvailable(int x, int y) {
        // Verifica se a posição está livre (não está ocupada por muros ou pelo herói)
        for (Element element : elements) {
            if (element.getPosition().getX() == x && element.getPosition().getY() == y) {
                return false;
            }
        }
        return true;
    }

    private void retrieveCoins() {
        // Verifica se o herói coletou alguma moeda e a remove da lista
        Position heroPos = hero.getPosition();
        List<Coin> coinsToRemove = new ArrayList<>();
        for (Coin coin : coins) {
            if (coin.getPosition().equals(heroPos)) {
                coinsToRemove.add(coin); // Marca a moeda para remoção
            }
        }
        coins.removeAll(coinsToRemove); // Remove todas as moedas coletadas
    }

    private void moveMonsters() {
        // Move todos os monstros para uma nova posição
        List<Monster> monstersToMove = new ArrayList<>(monsters); // Evita modificar a lista enquanto a percorre
        for (Monster monster : monstersToMove) {
            Position newMonsterPosition = monster.move();
            if (canHeroMove(newMonsterPosition)) {
                monster.setPosition(newMonsterPosition); // Atualiza a posição do monstro
            }
        }
    }

    private void verifyMonsterCollisions() {
        // Verifica se o herói colidiu com algum monstro
        for (Monster monster : monsters) {
            if (monster.getPosition().equals(hero.getPosition())) {
                System.out.println("Game Over! The hero was defeated by a monster.");
                System.exit(0); // Encerra o jogo
            }
        }
    }
}
