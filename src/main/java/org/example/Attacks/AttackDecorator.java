package org.example.Attacks;

import org.example.Pokemons.Pokemon;
import org.example.TypesOfObjects.PokemonType;
import org.example.Weathers.WeatherEffectStrategy;

public class AttackDecorator implements AttackStrategy {
    protected AttackStrategy decoratedAttack;

    public AttackDecorator(AttackStrategy attack) {
        this.decoratedAttack = attack;
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
        decoratedAttack.performAttack(attacker, defender, weatherEffect);
    }
}
