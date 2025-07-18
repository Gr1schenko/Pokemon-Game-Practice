package org.example.Attacks;

import org.example.Pokemons.Pokemon;
import org.example.TypesOfObjects.PokemonType;
import org.example.Weathers.WeatherEffectStrategy;

public class CooldownAttackDecorator extends AttackDecorator {
    private final int cooldown;
    private int cooldownEndTurn = -1;

    public CooldownAttackDecorator(AttackStrategy attack, int cooldown) {
        super(attack);
        this.cooldown = cooldown;
    }

    public boolean canUse(int currentTurn) {
        return currentTurn >= cooldownEndTurn;
    }

    public int turnsLeft(int currentTurn) {
        return Math.max(cooldownEndTurn - currentTurn, 0);
    }

    public void markUsed(int currentTurn) {
        this.cooldownEndTurn = currentTurn + cooldown + 1;
    }

    public void reset() {
        cooldownEndTurn = -1;
    }

    @Override
    public PokemonType getType() {
        return decoratedAttack.getType();
    }

    @Override
    public String getName() {
        return decoratedAttack.getName();
    }

    @Override
    public int calculateDamage(Pokemon attacker, WeatherEffectStrategy weatherEffect) {
        return decoratedAttack.calculateDamage(attacker, weatherEffect);
    }

    @Override
    public void performAttack(Pokemon attacker, Pokemon defender, WeatherEffectStrategy weatherEffect) {
        if (!canUse(attacker.getTurnCounter())) {
            return;
        }
        decoratedAttack.performAttack(attacker, defender, weatherEffect);
        markUsed(attacker.getTurnCounter());
    }
}