package org.example;

import org.example.Pokemons.Pokemon;
import org.example.Pokemons.EnemyPokemonFactory;
import org.example.TypesOfObjects.DifficultyType;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.Queue;

public class MapSystem {
    static final int ROWS = 10;
    static final int COLS = 10;

    static final int EMPTY = 0;
    static final int WALL = 1;
    static final int ENEMY = 2;
    static final int POTION = 3;
    static final int START = 4;
    static final int FINISH = 5;

    private static final int[][] EASY_MAP = {
            {1,1,1,1,1,1,1,1,1,1},
            {1,4,0,0,0,0,0,0,3,1},
            {1,0,1,1,0,1,1,0,0,1},
            {1,0,1,0,0,0,1,0,0,1},
            {1,0,1,0,1,0,1,1,0,1},
            {1,0,1,0,1,0,0,2,0,1},
            {1,0,1,0,1,1,1,1,0,1},
            {1,0,1,0,0,0,0,1,0,1},
            {1,2,0,0,1,1,0,1,5,1},
            {1,1,1,1,1,1,1,1,1,1}
    };

    private static final int[][] MEDIUM_MAP = {
            {1,1,1,1,1,1,1,1,1,1},
            {1,4,0,1,0,0,0,0,3,1},
            {1,0,0,1,0,1,1,0,0,1},
            {1,0,1,1,0,0,1,0,0,1},
            {1,0,1,2,0,0,1,1,2,1},
            {1,0,2,0,1,3,0,0,0,1},
            {1,0,1,0,1,1,1,1,0,1},
            {1,0,1,0,0,0,0,1,2,1},
            {1,2,0,0,1,1,0,0,5,1},
            {1,1,1,1,1,1,1,1,1,1}
    };

    private static final int[][] HARD_MAP = {
            {1,1,1,1,1,1,1,1,1,1},
            {1,4,1,1,2,0,0,0,2,1},
            {1,0,1,1,0,1,1,0,0,1},
            {1,2,1,1,0,0,1,0,0,1},
            {1,0,1,3,1,0,1,1,0,1},
            {1,0,2,0,1,0,0,0,0,1},
            {1,0,1,0,1,1,1,1,0,1},
            {1,0,1,0,0,0,0,1,2,1},
            {1,2,2,0,1,1,0,0,5,1},
            {1,1,1,1,1,1,1,1,1,1}
    };

    private int[][] map;
    private Map<Point, Pokemon> enemies;
    private int playerRow, playerCol;

    public MapSystem() {
        map = new int[ROWS][COLS];
        enemies = new HashMap<>();
    }

    public void loadPresetMap(DifficultyType difficulty) {
        int[][] preset;
        switch (difficulty) {
            case EASY:
                preset = EASY_MAP;
            break;
            case MEDIUM:
                preset = MEDIUM_MAP;
            break;
            case HARD:
                preset = HARD_MAP;
            break;
            default:
                preset = MEDIUM_MAP;
        }

        for (int r = 0; r < ROWS; r++) {
            System.arraycopy(preset[r], 0, map[r], 0, COLS);
        }

        enemies.clear();
        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLS; c++) {
                if (map[r][c] == ENEMY) {
                    int lvl = difficulty == DifficultyType.EASY ? 1 :
                            difficulty == DifficultyType.MEDIUM ? 3 : 4;
                    Pokemon enemy = new EnemyPokemonFactory().createPokemon("Противник ", lvl);
                    enemies.put(new Point(r, c), enemy);
                }
                if (map[r][c] == START) {
                    playerRow = r;
                    playerCol = c;
                }
            }
        }
    }

    public boolean isMapPassable() {
        Point start = new Point(1, 1);
        boolean[][] visited = new boolean[ROWS][COLS];
        Queue<Point> queue = new LinkedList<>();
        queue.add(start);
        visited[start.x][start.y] = true;

        while (!queue.isEmpty()) {
            Point p = queue.poll();
            int[] dr = {-1, 1, 0, 0};
            int[] dc = {0, 0, -1, 1};
            for (int i = 0; i < 4; i++) {
                int nr = p.x + dr[i];
                int nc = p.y + dc[i];
                if (nr >= 0 && nr < ROWS && nc >= 0 && nc < COLS) {
                    if (!visited[nr][nc] && map[nr][nc] != WALL) {
                        visited[nr][nc] = true;
                        queue.add(new Point(nr, nc));
                    }
                }
            }
        }

        if (!visited[ROWS - 2][COLS - 2]) {
            return false;
        }

        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLS; c++) {
                if (map[r][c] == POTION && !visited[r][c]) {
                    return false;
                }
            }
        }

        for (Point enemyPos : enemies.keySet()) {
            if (!visited[enemyPos.x][enemyPos.y]) {
                return false;
            }
        }
        return true;
    }

    public void generateRandomMap(DifficultyType difficulty) {
        for (int r = 0; r < ROWS; r++) {
            Arrays.fill(map[r], EMPTY);
        }
        for (int i = 0; i < ROWS; i++) {
            map[i][0] = WALL;
            map[i][COLS - 1] = WALL;
        }
        for (int j = 0; j < COLS; j++) {
            map[0][j] = WALL;
            map[ROWS - 1][j] = WALL;
        }

        map[1][1] = START;
        map[ROWS - 2][COLS - 2] = FINISH;

        List<Point> freePoints = new ArrayList<>();
        for (int r = 1; r < ROWS - 1; r++) {
            for (int c = 1; c < COLS - 1; c++) {
                if (map[r][c] == EMPTY) {
                    freePoints.add(new Point(r, c));
                }
            }
        }

        Collections.shuffle(freePoints);

        int potionCount = difficulty == DifficultyType.EASY ? 3 :
                difficulty == DifficultyType.MEDIUM ? 2 : 1;

        for (int i = 0; i < potionCount && !freePoints.isEmpty(); i++) {
            Point p = freePoints.remove(0);
            map[p.x][p.y] = POTION;
        }

        int enemyCount = difficulty == DifficultyType.EASY ? 3 :
                difficulty == DifficultyType.MEDIUM ? 5 : 7;

        int lvl = difficulty == DifficultyType.EASY ? 1 :
                difficulty == DifficultyType.MEDIUM ? 3 : 4;

        enemies.clear();
        for (int i = 0; i < enemyCount && !freePoints.isEmpty(); i++) {
            Point p = freePoints.remove(0);
            map[p.x][p.y] = ENEMY;
            Pokemon enemy = new EnemyPokemonFactory().createPokemon("Противник " + (i + 1), lvl);
            enemies.put(p, enemy);
        }

        int wallCount = difficulty == DifficultyType.EASY ? 7 :
                difficulty == DifficultyType.MEDIUM ? 11 : 17;
        for (int i = 0; i < wallCount && i < freePoints.size(); i++) {
            Point p = freePoints.get(i);
            map[p.x][p.y] = WALL;
        }

        while (!isMapPassable()) {
            generateRandomMap(difficulty);
        }

        playerRow = 1;
        playerCol = 1;
    }

    public int[][] getMap() {
        return map;
    }

    public void setMap(int[][] map) {
        this.map = map;
    }

    public Map<Point, Pokemon> getEnemies() {
        return enemies;
    }

    public void setEnemies(Map<Point, Pokemon> enemies) {
        this.enemies = enemies;
    }

    public int getPlayerRow() {
        return playerRow;
    }

    public void setPlayerRow(int playerRow) {
        this.playerRow = playerRow;
    }

    public int getPlayerCol() {
        return playerCol;
    }

    public void setPlayerCol(int playerCol) {
        this.playerCol = playerCol;
    }
}