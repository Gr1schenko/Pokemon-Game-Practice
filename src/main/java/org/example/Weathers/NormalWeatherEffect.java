package org.example.Weathers;

import org.example.TypesOfObjects.PokemonType;

public class NormalWeatherEffect implements WeatherEffectStrategy {
    @Override
    public double getDamageModifier(PokemonType attackType) {
        return 1.0;
    }
}
