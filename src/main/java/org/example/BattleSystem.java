package org.example;

import org.example.Attacks.AttackStrategy;
import org.example.Attacks.CooldownAttackDecorator;
import org.example.Pokemons.Pokemon;
import org.example.Weathers.WeatherEffectStrategy;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class BattleSystem {
    private PokemonGame game;
    private GameUI gameUI;
    private WeatherEffectStrategy weatherEffect;
    private Pokemon currentEnemy;
    private JPanel battlePanel;
    private JTextArea battleLog;

    public BattleSystem(PokemonGame game) {
        this.game = game;
        this.weatherEffect = game.getWeatherEffect();
        this.gameUI = game.getGameUI();
    }

    public void startBattle(Pokemon enemy) {
        currentEnemy = enemy;

        for (Pokemon p : game.getPlayerTeam()) {
            for (AttackStrategy attack : p.getAttacks()) {
                if (attack instanceof CooldownAttackDecorator) {
                    ((CooldownAttackDecorator) attack).reset();
                }
            }
        }

        gameUI.printStatus("Битва с " + enemy.getName() + " началась");

        battlePanel = new JPanel(new BorderLayout());
        battlePanel.setPreferredSize(new Dimension((int) (game.getWidth()/2), game.getHeight()));

        battleLog = new JTextArea();
        battleLog.setEditable(false);
        battleLog.setFont(new Font("Monospaced", Font.PLAIN, 12));
        battleLog.setLineWrap(true);
        battleLog.setWrapStyleWord(true);

        game.add(battlePanel, BorderLayout.EAST);
        game.revalidate();
        game.repaint();

        updateBattleUI();
    }

    private void updateBattleUI() {
        battlePanel.removeAll();
        Pokemon currentPokemon = game.getFirstAlivePokemon();
        if (currentPokemon == null) {
            battleLog.append("Все ваши покемоны без сознания. Вы проиграли битву\n");
            endBattle(false);
            return;
        }

        JPanel infoPanel = new JPanel(new GridLayout(2, 1));
        infoPanel.add(new JLabel("Ваш покемон: " + currentPokemon.getName()));
        infoPanel.add(new JLabel("Противник: " + currentEnemy.getName()));

        JPanel attackPanel = new JPanel(new GridLayout(1, currentPokemon.getAttacks().size()));

        for (int i = 0; i < currentPokemon.getAttacks().size(); i++) {
            AttackStrategy attack = currentPokemon.getAttacks().get(i);
            JButton attackButton = createAttackButton(currentPokemon, attack);
            attackPanel.add(attackButton);
        }

        battlePanel.add(infoPanel, BorderLayout.NORTH);
        battlePanel.add(new JScrollPane(battleLog), BorderLayout.CENTER);
        battlePanel.add(attackPanel, BorderLayout.SOUTH);
        battlePanel.revalidate();
        battlePanel.repaint();
    }

    private JButton createAttackButton(Pokemon currentPokemon, AttackStrategy attack) {
        JButton attackButton = new JButton(attack.getName());

        if (attack instanceof CooldownAttackDecorator) {
            CooldownAttackDecorator cdAttack = (CooldownAttackDecorator) attack;
            int turnsLeft = cdAttack.turnsLeft(currentPokemon.getTurnCounter());

            if (turnsLeft > 0) {
                attackButton.setEnabled(false);
                attackButton.setText(attack.getName() + " (" + turnsLeft + ")");
                attackButton.setToolTipText("Доступна через " + turnsLeft + " ходов");
            } else {
                attackButton.setEnabled(true);
                attackButton.setText(attack.getName());
            }
        }

        attackButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performPlayerAttack(currentPokemon, attack);
            }
        });

        return attackButton;
    }

    private void performPlayerAttack(Pokemon currentPokemon, AttackStrategy attack) {
        int damage = attack.calculateDamage(currentPokemon, weatherEffect);
        attack.performAttack(currentPokemon, currentEnemy, weatherEffect);

        battleLog.append(currentPokemon.getName() + " использует " +
                attack.getName() + " и наносит " + damage + " урона\n");
        battleLog.append("У " + currentEnemy.getName() + " осталось HP: " +
                currentEnemy.getCurrentHP() + "/" + currentEnemy.getMaxHP() + "\n");

        if (currentEnemy.getCurrentHP() <= 0) {
            battleLog.append(currentEnemy.getName() + " повержен\n");
            endBattle(true);
            return;
        }

        enemyTurn(currentPokemon);

        if (currentPokemon.getCurrentHP() <= 0) {
            battleLog.append(currentPokemon.getName() + " без сознания\n");
            if (game.anyPlayerAlive()) {
                SwingUtilities.invokeLater(this::updateBattleUI);
            } else {
                battleLog.append("Все ваши покемоны без сознания. Вы проиграли битву\n");
                endBattle(false);
            }
        } else {
            currentPokemon.incrementTurn();
            SwingUtilities.invokeLater(this::updateBattleUI);
        }
    }

    private void enemyTurn(Pokemon currentPokemon) {
        if (currentEnemy != null && game.anyPlayerAlive()) {
            Random rand = new Random();
            int attackIndex = rand.nextInt(currentEnemy.getAttacks().size());
            AttackStrategy enemyAttack = currentEnemy.getAttacks().get(attackIndex);

            Pokemon target = game.getFirstAlivePokemon();
            if (target == null) return;

            int damage = enemyAttack.calculateDamage(currentEnemy, weatherEffect);
            enemyAttack.performAttack(currentEnemy, target, weatherEffect);
            battleLog.append(currentEnemy.getName() + " использует " + enemyAttack.getName() +
                    " против " + target.getName() + " и наносит " + damage + " урона" +
                    "\n" + "У " + target.getName() + " осталось HP: " +
                    target.getCurrentHP() + "/" + target.getMaxHP() + "\n");
        }
    }

    private void endBattle(boolean victory) {
        game.remove(battlePanel);
        game.revalidate();
        game.repaint();

        if (victory) {
            game.getMapSystem().getMap()[game.getMapSystem().getPlayerRow()][game.getMapSystem().getPlayerCol()] = MapSystem.EMPTY;
            game.getMapSystem().getEnemies().remove(new Point(game.getMapSystem().getPlayerRow(), game.getMapSystem().getPlayerCol()));
            gameUI.printStatus("Вы победили в битве");

            Pokemon winner = game.getFirstAlivePokemon();
            if (winner != null) {
                int exp = (int) (currentEnemy.getMaxHP() * 0.4);
                winner.gainExperience(exp);
                gameUI.printStatus(winner.getName() + " получил " + exp + " опыта");
                game.tryEvolve(winner);
            }

            game.setInBattle(false);
            game.requestFocusInWindow();
        } else {
            gameUI.printStatus("Все ваши покемоны повержены. Игра окончена");
            JOptionPane.showMessageDialog(game,
                    "Все ваши покемоны повержены\nИгра завершена",
                    "Поражение",
                    JOptionPane.INFORMATION_MESSAGE);
            System.exit(0);
        }
        currentEnemy = null;
    }
}