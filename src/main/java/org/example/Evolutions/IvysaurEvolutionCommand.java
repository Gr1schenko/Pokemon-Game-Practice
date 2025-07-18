package org.example.Evolutions;

import org.example.Pokemons.Ivysaur;
import org.example.Pokemons.Pokemon;
import org.example.Pokemons.Venusaur;

public class IvysaurEvolutionCommand implements EvolutionCommand {
    @Override
    public boolean canEvolve(Pokemon pokemon) {
        return (pokemon instanceof Ivysaur && pokemon.getLevel() >= 6 && pokemon.getEvolutionStage() == 2);
    }

    @Override
    public Pokemon execute(Pokemon pokemon) {
        if (canEvolve(pokemon)) {
            Venusaur venusaur = new Venusaur(pokemon.getLevel());
            venusaur.setEvolutionStage(3);
            return venusaur;
        }
        return null;
    }
}
