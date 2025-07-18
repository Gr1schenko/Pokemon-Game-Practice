package org.example.Evolutions;

import org.example.Pokemons.Pokemon;

public interface EvolutionCommand {
    boolean canEvolve(Pokemon pokemon);
    Pokemon execute(Pokemon pokemon);
}
