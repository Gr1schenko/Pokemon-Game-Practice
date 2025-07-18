package org.example;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            PokemonGame game = new PokemonGame();
            game.setVisible(true);
        });
    }
}