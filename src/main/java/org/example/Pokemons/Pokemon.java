package org.example.Pokemons;

import org.example.Attacks.AttackStrategy;
import org.example.Attacks.DefaultAttack;
import org.example.TypesOfObjects.PokemonType;
import org.example.Weathers.WeatherEffectStrategy;

import java.util.ArrayList;
import java.util.List;

public abstract class Pokemon {
    protected PokemonType type;
    protected String name;
    protected int level;
    protected int maxHP;
    protected int currentHP;
    protected int experience;
    protected List<AttackStrategy> attacks = new ArrayList<>();
    protected int evolutionStage;
    private int turnCounter = 0;

    public Pokemon(PokemonType type, String name, int level, int maxHP, int evolutionStage) {
        this.type = type;
        this.name = name;
        this.level = level;
        this.maxHP = maxHP;
        this.currentHP = maxHP;
        this.experience = 0;
        this.evolutionStage = evolutionStage;
        attacks.add(new DefaultAttack());
        initializeAttacks();
    }

    protected abstract void initializeAttacks();

    public void performAttack(int attackIndex, Pokemon defender, WeatherEffectStrategy weatherEffect) {
        if (attackIndex >= 0 && attackIndex < attacks.size()) {
            attacks.get(attackIndex).performAttack(this, defender, weatherEffect);
        }
    }

    public void takeDamage(int damage) {
        currentHP -= damage;
        if (currentHP < 0) {
            currentHP = 0;
        }
    }

    public void heal() {
        if (currentHP <= 0) {
            experience = 0;
        }
        currentHP = maxHP;
    }

    protected int experienceToNextLevel() {
        return 35 + (level - 1) * 10;
    }

    protected void levelUp() {
        level++;
        maxHP += 5;
        currentHP = maxHP;
    }

    public void incrementTurn() {
        turnCounter++;
    }

    public void gainExperience(int exp) {
        experience += exp;
        while (experience >= experienceToNextLevel()) {
            experience -= experienceToNextLevel();
            levelUp();
        }
    }

    public PokemonType getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public int getLevel() {
        return level;
    }

    public int getMaxHP() {
        return maxHP;
    }

    public int getCurrentHP() {
        return currentHP;
    }

    public List<AttackStrategy> getAttacks() {
        return attacks;
    }

    public int getEvolutionStage() {
        return evolutionStage;
    }

    public void setEvolutionStage(int stage) {
        this.evolutionStage = stage;
    }

    public int getTurnCounter() {
        return turnCounter;
    }
}