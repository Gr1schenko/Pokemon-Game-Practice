package org.example.Weathers;

import org.example.TypesOfObjects.PokemonType;

public interface WeatherEffectStrategy {
    double getDamageModifier(PokemonType attackType);
}
