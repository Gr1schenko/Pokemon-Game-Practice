package org.example;

import org.example.Pokemons.Pokemon;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class GameUI {
    private JPanel gridPanel;
    private JLabel[][] cells;
    private JTextArea statusArea;

    private ImageIcon playerIcon;
    private ImageIcon potionIcon;
    private ImageIcon enemyIcon;
    private ImageIcon finishIcon;
    private ImageIcon startIcon;

    private String AshImagePath = "D:\\yuliy\\IdeaProjects\\Pokemons\\src\\main\\resources\\Ash.png";
    private String PotionImagePath= "D:\\yuliy\\IdeaProjects\\Pokemons\\src\\main\\resources\\Potion.png";
    private String EnemyImagePath  = "D:\\yuliy\\IdeaProjects\\Pokemons\\src\\main\\resources\\TeamR.png";
    private String FinishImagePath= "D:\\yuliy\\IdeaProjects\\Pokemons\\src\\main\\resources\\Finish.png";
    private String StartImagePath= "D:\\yuliy\\IdeaProjects\\Pokemons\\src\\main\\resources\\Start.png";

    public GameUI(PokemonGame game) {
        initializeUI(game);
    }

    private void initializeUI(PokemonGame game) {
        game.setTitle("Pokemon Game");
        game.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        game.setSize(game.getPreferredWindowWidth(), game.getPreferredWindowHeight());
        game.setLocationRelativeTo(null);
        game.setLayout(new BorderLayout());

        initializeGrid();
        game.add(gridPanel, BorderLayout.CENTER);

        statusArea = new JTextArea(7, 40);
        statusArea.setEditable(false);
        statusArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        statusArea.setLineWrap(true);
        statusArea.setFocusable(false);

        JScrollPane scrollPane = new JScrollPane(statusArea);
        scrollPane.setPreferredSize(new Dimension(game.getWidth(), (int) (game.getHeight() * 0.2)));
        game.add(scrollPane, BorderLayout.SOUTH);

        ImageIcon originalPlayerIcon = new ImageIcon(AshImagePath);
        if (originalPlayerIcon.getImageLoadStatus() == MediaTracker.COMPLETE) {
            playerIcon = originalPlayerIcon;
        } else {
            System.err.println("Ошибка загрузки изображения игрока");
        }

        ImageIcon originalPotionIcon = new ImageIcon(PotionImagePath);
        if (originalPotionIcon.getImageLoadStatus() == MediaTracker.COMPLETE) {
            potionIcon = originalPotionIcon;
        } else {
            System.err.println("Ошибка загрузки изображения зелья");
        }

        ImageIcon originalEnemyIcon = new ImageIcon(EnemyImagePath);
        if (originalEnemyIcon.getImageLoadStatus() == MediaTracker.COMPLETE) {
            enemyIcon = originalEnemyIcon;
        } else {
            System.err.println("Ошибка загрузки изображения врага");
        }

        ImageIcon originalFinishIcon = new ImageIcon(FinishImagePath);
        if (originalFinishIcon.getImageLoadStatus() == MediaTracker.COMPLETE) {
            finishIcon = originalFinishIcon;
        } else {
            System.err.println("Ошибка загрузки изображения финиша");
        }

        ImageIcon originalStartIcon = new ImageIcon(StartImagePath);
        if (originalStartIcon.getImageLoadStatus() == MediaTracker.COMPLETE) {
            startIcon = originalStartIcon;
        } else {
            System.err.println("Ошибка загрузки изображения старта");
        }
    }

    private void initializeGrid() {
        gridPanel = new JPanel(new GridLayout(MapSystem.ROWS, MapSystem.COLS));
        cells = new JLabel[MapSystem.ROWS][MapSystem.COLS];

        for (int r = 0; r < MapSystem.ROWS; r++) {
            for (int c = 0; c < MapSystem.COLS; c++) {
                JLabel cell = new JLabel("", SwingConstants.CENTER);
                cell.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                cell.setOpaque(true);
                cells[r][c] = cell;
                gridPanel.add(cell);
            }
        }
    }

    public void updateUI(int[][] map, int playerRow, int playerCol, Map<Point, Pokemon> enemies) {
        for (int r = 0; r < MapSystem.ROWS; r++) {
            for (int c = 0; c < MapSystem.COLS; c++) {
                cells[r][c].setText("");
                cells[r][c].setIcon(null);
                if (r == playerRow && c == playerCol) {
                    if (playerIcon != null && cells[r][c].getWidth() > 0 && cells[r][c].getHeight() > 0) {
                        cells[r][c].setIcon(getScaledIcon(playerIcon, cells[r][c].getWidth(), cells[r][c].getHeight()));
                    } else {
                        cells[r][c].setBackground(Color.YELLOW);
                        cells[r][c].setText("Игрок");
                    }
                } else {
                    switch (map[r][c]) {
                        case MapSystem.EMPTY:
                            cells[r][c].setBackground(Color.WHITE);
                            break;
                        case MapSystem.WALL:
                            cells[r][c].setBackground(Color.DARK_GRAY);
                            break;
                        case MapSystem.ENEMY:
                            if (enemyIcon != null && cells[r][c].getWidth() > 0 && cells[r][c].getHeight() > 0) {
                                cells[r][c].setIcon(getScaledIcon(enemyIcon, cells[r][c].getWidth(), cells[r][c].getHeight()));
                            } else {
                                cells[r][c].setBackground(Color.RED);
                                cells[r][c].setText("В");
                            }
                            break;
                        case MapSystem.POTION:
                            if (potionIcon != null && cells[r][c].getWidth() > 0 && cells[r][c].getHeight() > 0) {
                                cells[r][c].setIcon(getScaledIcon(potionIcon, cells[r][c].getWidth(), cells[r][c].getHeight()));
                            } else {
                                cells[r][c].setBackground(Color.GREEN);
                                cells[r][c].setText("З");
                            }
                            break;
                        case MapSystem.START:
                            if (startIcon != null && cells[r][c].getWidth() > 0 && cells[r][c].getHeight() > 0) {
                                cells[r][c].setIcon(getScaledIcon(startIcon, cells[r][c].getWidth(), cells[r][c].getHeight()));
                            } else {
                            cells[r][c].setBackground(Color.BLUE);
                            cells[r][c].setText("Старт");
                        }
                            break;
                        case MapSystem.FINISH:
                            if (finishIcon != null && cells[r][c].getWidth() > 0 && cells[r][c].getHeight() > 0) {
                                cells[r][c].setIcon(getScaledIcon(finishIcon, cells[r][c].getWidth(), cells[r][c].getHeight()));
                            } else {
                                cells[r][c].setBackground(Color.ORANGE);
                                cells[r][c].setText("Финиш");
                            }
                            break;
                        default:
                            cells[r][c].setBackground(Color.WHITE);
                            break;
                    }
                }
            }
        }
    }

    public void printStatus(String msg) {
        statusArea.append(msg + "\n");
        statusArea.setCaretPosition(statusArea.getDocument().getLength());
    }

    private ImageIcon getScaledIcon(ImageIcon icon, int maxWidth, int maxHeight) {
        if (icon == null) return null;

        Image originalImage = icon.getImage();
        int imgWidth = originalImage.getWidth(null);
        int imgHeight = originalImage.getHeight(null);

        if (imgWidth <= 0 || imgHeight <= 0) {
            return icon;
        }

        double scale = Math.min((double) maxWidth / imgWidth, (double) maxHeight / imgHeight);

        int newWidth = (int) (imgWidth * scale);
        int newHeight = (int) (imgHeight * scale);

        Image scaledImage = originalImage.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
        return new ImageIcon(scaledImage);
    }
}