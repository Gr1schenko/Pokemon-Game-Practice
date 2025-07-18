package org.example;

import org.example.Evolutions.*;
import org.example.Pokemons.Pokemon;
import org.example.Pokemons.PlayerPokemonFactory;
import org.example.TypesOfObjects.DifficultyType;
import org.example.Weathers.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

public class PokemonGame extends JFrame implements KeyListener {
    private MapSystem mapSystem;
    private GameUI gameUI;
    private BattleSystem battleSystem;

    private DifficultyType difficulty;
    private WeatherEffectStrategy weatherEffect;

    private List<Pokemon> playerTeam;
    private List<EvolutionCommand> evolutionCommands;

    private boolean inBattle = false;

    private PlayerPokemonFactory playerFactory;

    private final int preferredWindowWidth = 700;
    private final int preferredWindowHeight = 700;

    public PokemonGame() {
        mapSystem = new MapSystem();
        gameUI = new GameUI(this);

        difficulty = DifficultyType.MEDIUM;

        playerTeam = new ArrayList<>();
        evolutionCommands = new ArrayList<>();

        playerFactory = new PlayerPokemonFactory();

        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);

        initializeEvolutionCommands();
        startGame();
    }

    private void initializeEvolutionCommands() {
        evolutionCommands.add(new PikachuEvolutionCommand());
        evolutionCommands.add(new CharmanderEvolutionCommand());
        evolutionCommands.add(new BulbasaurEvolutionCommand());
        evolutionCommands.add(new PidgeyEvolutionCommand());
        evolutionCommands.add(new SquirtleEvolutionCommand());
        evolutionCommands.add(new WartortleEvolutionCommand());
        evolutionCommands.add(new IvysaurEvolutionCommand());
    }

    private void startGame() {
        selectDifficulty();
        selectWeather();
        battleSystem = new BattleSystem(this);
        selectPlayerTeam();

        String[] options = {"Случайная карта", "Заготовленная карта"};
        int choice = JOptionPane.showOptionDialog(this, "Выберите тип карты:", "Тип карты",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

        if (choice == 1) {
            mapSystem.loadPresetMap(difficulty);
        } else {
            mapSystem.generateRandomMap(difficulty);
        }

        gameUI.updateUI(mapSystem.getMap(), mapSystem.getPlayerRow(), mapSystem.getPlayerCol(), mapSystem.getEnemies());
    }

    private void selectDifficulty() {
        String[] options = {"Легкий", "Средний", "Сложный"};
        int choice = JOptionPane.showOptionDialog(this, "Выберите уровень сложности:", "Уровень",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[1]);

        if (checkCancelled(choice)) {
            System.exit(0);
        }

        difficulty = DifficultyType.values()[choice];
    }

    private void selectWeather() {
        String[] options = {"Обычная", "Дождь", "Солнечно", "Ветренно"};
        int choice = JOptionPane.showOptionDialog(this, "Выберите погодные условия:", "Погода",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

        if (checkCancelled(choice)) {
            System.exit(0);
        }

        switch (choice) {
            case 0:
                weatherEffect = new NormalWeatherEffect();
                gameUI.printStatus("Нормальная погода");
                break;
            case 1:
                weatherEffect = new RainWeatherEffect();
                gameUI.printStatus("Дождливая погода");
                break;
            case 2:
                weatherEffect = new SunnyWeatherEffect();
                gameUI.printStatus("Солнечная погода");
                break;
            case 3:
                weatherEffect = new WindWeatherEffect();
                gameUI.printStatus("Ветренная погода");
                break;
            default:
                weatherEffect = new NormalWeatherEffect();
                gameUI.printStatus("Нормальная погода");
        }
    }

    private void selectPlayerTeam() {
        playerTeam.clear();
        String[] choices = {"Пикачу", "Чармандер", "Бульбазавр", "Пиджи", "Сквиртл"};

        for (int i = 0; i < 3; i++) {
            String choice = (String) JOptionPane.showInputDialog(
                    this,
                    "Выберите покемона " + (i + 1),
                    "Формирование команды",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    choices,
                    choices[0]);

            if (choice == null) {
                System.exit(0);
            }

            Pokemon pokemon = playerFactory.createPokemon(choice, 1);
            playerTeam.add(pokemon);
            gameUI.printStatus("Добавлен " + pokemon.getName() + " в вашу команду");
        }
    }

    public void tryEvolve(Pokemon pokemon) {
        for (EvolutionCommand cmd : evolutionCommands) {
            if (cmd.canEvolve(pokemon)) {
                Pokemon evolved = cmd.execute(pokemon);
                if (evolved != null) {
                    int idx = playerTeam.indexOf(pokemon);
                    if (idx != -1) {
                        playerTeam.set(idx, evolved);
                        gameUI.printStatus(pokemon.getName() + " эволюционировал в " + evolved.getName());
                    }
                    break;
                }
            }
        }
    }

    private boolean checkCancelled(Integer result) {
        return result == null || result == JOptionPane.CLOSED_OPTION;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (inBattle) return;

        switch (e.getKeyCode()) {
            case KeyEvent.VK_W:
                movePlayer(-1, 0);
                break;
            case KeyEvent.VK_S:
                movePlayer(1, 0);
            break;
            case KeyEvent.VK_A:
                movePlayer(0, -1);
            break;
            case KeyEvent.VK_D:
                movePlayer(0, 1);
            break;
        }
    }

    void movePlayer(int dr, int dc) {
        int newR = mapSystem.getPlayerRow() + dr;
        int newC = mapSystem.getPlayerCol() + dc;

        if (newR < 0 || newR >= MapSystem.ROWS || newC < 0 || newC >= MapSystem.COLS) {
            gameUI.printStatus("Нельзя выйти за пределы карты");
            return;
        }

        if (mapSystem.getMap()[newR][newC] == MapSystem.WALL) {
            gameUI.printStatus("Здесь стена, сюда нельзя пройти");
            return;
        }

        mapSystem.setPlayerRow(newR);
        mapSystem.setPlayerCol(newC);

        handleCellAction();
        gameUI.updateUI(mapSystem.getMap(), mapSystem.getPlayerRow(), mapSystem.getPlayerCol(), mapSystem.getEnemies());
        checkWinCondition();
    }

    private void handleCellAction() {
        switch (mapSystem.getMap()[mapSystem.getPlayerRow()][mapSystem.getPlayerCol()]) {
            case MapSystem.POTION:
                healTeam();
                mapSystem.getMap()[mapSystem.getPlayerRow()][mapSystem.getPlayerCol()] = MapSystem.EMPTY;
                break;
            case MapSystem.ENEMY:
                battleSystem.startBattle(mapSystem.getEnemies().get(new Point(mapSystem.getPlayerRow(), mapSystem.getPlayerCol())));
                inBattle = true;
                break;
            case MapSystem.FINISH:
                if (mapSystem.getEnemies().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Вы победили! Игра окончена");
                } else {
                    gameUI.printStatus("Достигните финиша после победы над всеми врагами");
                }
                break;
        }
    }

    private void healTeam() {
        for (Pokemon p : playerTeam) {
            p.heal();
        }
        gameUI.printStatus("Вы нашли зелье. Здоровье команды восстановлено");
    }

    public boolean anyPlayerAlive() {
        for (Pokemon p : playerTeam) {
            if (p.getCurrentHP() > 0) {
                return true;
            }
        }
        return false;
    }

    public Pokemon getFirstAlivePokemon() {
        for (Pokemon p : playerTeam) {
            if (p.getCurrentHP() > 0) {
                return p;
            }
        }
        return null;
    }

    void checkWinCondition() {
        if (mapSystem.getEnemies().isEmpty() && mapSystem.getMap()[mapSystem.getPlayerRow()][mapSystem.getPlayerCol()] == MapSystem.FINISH) {
            System.exit(0);
        }
    }

    public WeatherEffectStrategy getWeatherEffect() {
        return weatherEffect;
    }

    public GameUI getGameUI() {
        return gameUI;
    }

    public MapSystem getMapSystem() {
        return mapSystem;
    }

    public int getPreferredWindowWidth() {
        return preferredWindowWidth;
    }

    public int getPreferredWindowHeight() {
        return preferredWindowHeight;
    }

    public List<Pokemon> getPlayerTeam() {
        return playerTeam;
    }

    public void setInBattle(boolean inBattle) {
        this.inBattle = inBattle;
    }

    @Override public void keyTyped(KeyEvent e) {}

    @Override public void keyReleased(KeyEvent e) {}
}