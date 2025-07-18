package org.example.Weathers;

import org.example.TypesOfObjects.PokemonType;

public class WindWeatherEffect implements WeatherEffectStrategy {
    @Override
    public double getDamageModifier(PokemonType attackType) {
        switch (attackType) {
            case FLYING:
                return 1.5;
            case FIRE:
                return 0.7;
            case GRASS:
                return 0.7;
            default:
                return 1.0;
        }
    }
}
