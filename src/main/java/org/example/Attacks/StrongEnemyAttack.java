package org.example.Attacks;

import org.example.Pokemons.Pokemon;
import org.example.TypesOfObjects.PokemonType;
import org.example.Weathers.WeatherEffectStrategy;

public class StrongEnemyAttack implements AttackStrategy {
    @Override
    public PokemonType getType() {
        return PokemonType.NONTYPE;
    }

    @Override
    public String getName() {
        return "Сильная атака";
    }

    @Override
    public int calculateDamage(Pokemon attacker, WeatherEffectStrategy weatherEffect) {
        return (int) ((15 + 5 * attacker.getLevel()) * weatherEffect.getDamageModifier(getType()));
    }

    @Override
    public void performAttack(Pokemon attacker, Pokemon defender, WeatherEffectStrategy weatherEffect) {
        int damage = calculateDamage(attacker, weatherEffect);
        defender.takeDamage(damage);
    }
}
