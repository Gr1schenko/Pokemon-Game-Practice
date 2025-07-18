package org.example.Evolutions;

import org.example.Pokemons.Bulbasaur;
import org.example.Pokemons.Ivysaur;
import org.example.Pokemons.Pokemon;

public class BulbasaurEvolutionCommand implements EvolutionCommand {
    @Override
    public boolean canEvolve(Pokemon pokemon) {
        return (pokemon instanceof Bulbasaur && pokemon.getLevel() >= 3 && pokemon.getEvolutionStage() == 1);
    }

    @Override
    public Pokemon execute(Pokemon pokemon) {
        if (canEvolve(pokemon)) {
            Ivysaur ivysaur = new Ivysaur(pokemon.getLevel());
            ivysaur.setEvolutionStage(2);
            return ivysaur;
        }
        return null;
    }
}
