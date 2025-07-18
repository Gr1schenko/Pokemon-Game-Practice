package org.example.Evolutions;

import org.example.Pokemons.Charmander;
import org.example.Pokemons.Charmeleon;
import org.example.Pokemons.Pokemon;

public class CharmanderEvolutionCommand implements EvolutionCommand {
    @Override
    public boolean canEvolve(Pokemon pokemon) {
        return (pokemon instanceof Charmander && pokemon.getLevel() >= 3 && pokemon.getEvolutionStage() == 1);
    }

    @Override
    public Pokemon execute(Pokemon pokemon) {
        if (canEvolve(pokemon)) {
            Charmeleon charmeleon = new Charmeleon(pokemon.getLevel());
            charmeleon.setEvolutionStage(2);
            return charmeleon;
        }
        return null;
    }
}
