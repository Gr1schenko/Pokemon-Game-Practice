package org.example.Evolutions;

import org.example.Pokemons.Pidgeotto;
import org.example.Pokemons.Pidgey;
import org.example.Pokemons.Pokemon;

public class PidgeyEvolutionCommand implements EvolutionCommand {
    @Override
    public boolean canEvolve(Pokemon pokemon) {
        return (pokemon instanceof Pidgey && pokemon.getLevel() >= 3 && pokemon.getEvolutionStage() == 1);
    }

    @Override
    public Pokemon execute(Pokemon pokemon) {
        if (canEvolve(pokemon)) {
            Pidgeotto pidgeotto = new Pidgeotto(pokemon.getLevel());
            pidgeotto.setEvolutionStage(2);
            return pidgeotto;
        }
        return null;
    }
}