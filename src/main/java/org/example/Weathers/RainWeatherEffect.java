package org.example.Weathers;

import org.example.TypesOfObjects.PokemonType;

public class RainWeatherEffect implements WeatherEffectStrategy {
    @Override
    public double getDamageModifier(PokemonType attackType) {
        switch (attackType) {
            case WATER:
                return 1.5;
            case ELECTRIC:
                return 1.3;
            case FIRE:
                return 0.5;
            case FLYING:
                return 0.7;
            default:
                return 1.0;
        }
    }
}
