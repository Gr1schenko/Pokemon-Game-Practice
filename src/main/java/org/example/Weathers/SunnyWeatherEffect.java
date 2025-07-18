package org.example.Weathers;

import org.example.TypesOfObjects.PokemonType;

public class SunnyWeatherEffect implements WeatherEffectStrategy {
    @Override
    public double getDamageModifier(PokemonType attackType) {
        switch (attackType) {
            case WATER:
                return 0.5;
            case ELECTRIC:
                return 0.7;
            case FIRE:
                return 1.3;
            case GRASS:
                return 1.5;
            default:
                return 1.0;
        }
    }
}
