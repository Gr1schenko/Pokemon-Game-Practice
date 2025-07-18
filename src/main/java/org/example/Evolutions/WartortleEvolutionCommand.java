package org.example.Evolutions;

import org.example.Pokemons.Blastoise;
import org.example.Pokemons.Pokemon;
import org.example.Pokemons.Wartortle;

public class WartortleEvolutionCommand  implements EvolutionCommand {
    @Override
    public boolean canEvolve(Pokemon pokemon) {
        return (pokemon instanceof Wartortle && pokemon.getLevel() >= 6 && pokemon.getEvolutionStage() == 2);
    }

    @Override
    public Pokemon execute(Pokemon pokemon) {
        if (canEvolve(pokemon)) {
            Blastoise blastoise = new Blastoise(pokemon.getLevel());
            blastoise.setEvolutionStage(3);
            return blastoise;
        }
        return null;
    }
}
