package org.example.Attacks;

import org.example.Pokemons.Pokemon;
import org.example.TypesOfObjects.PokemonType;
import org.example.Weathers.WeatherEffectStrategy;

public interface AttackStrategy {
    PokemonType getType();
    String getName();
    int calculateDamage(Pokemon attacker, WeatherEffectStrategy weatherEffect);
    void performAttack(Pokemon attacker, Pokemon defender, WeatherEffectStrategy weatherEffect);
}
