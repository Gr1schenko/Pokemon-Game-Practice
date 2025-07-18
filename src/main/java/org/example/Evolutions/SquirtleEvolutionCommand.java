package org.example.Evolutions;

import org.example.Pokemons.Pokemon;
import org.example.Pokemons.Squirtle;
import org.example.Pokemons.Wartortle;

public class SquirtleEvolutionCommand implements EvolutionCommand {
    @Override
    public boolean canEvolve(Pokemon pokemon) {
        return (pokemon instanceof Squirtle && pokemon.getLevel() >= 3 && pokemon.getEvolutionStage() == 1);
    }

    @Override
    public Pokemon execute(Pokemon pokemon) {
        if (canEvolve(pokemon)) {
            Wartortle wartortle = new Wartortle(pokemon.getLevel());
            wartortle.setEvolutionStage(2);
            return wartortle;
        }
        return null;
    }
}
