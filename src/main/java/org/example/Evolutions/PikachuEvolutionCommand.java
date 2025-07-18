package org.example.Evolutions;

import org.example.Pokemons.Pikachu;
import org.example.Pokemons.Pokemon;
import org.example.Pokemons.Raichu;

public class PikachuEvolutionCommand implements EvolutionCommand {
    @Override
    public boolean canEvolve(Pokemon pokemon) {
        return (pokemon instanceof Pikachu && pokemon.getLevel() >= 3 && pokemon.getEvolutionStage() == 1);
    }

    @Override
    public Pokemon execute(Pokemon pokemon) {
        if (canEvolve(pokemon)) {
            Raichu raichu = new Raichu(pokemon.getLevel());
            raichu.setEvolutionStage(2);
            return raichu;
        }
        return null;
    }
}
