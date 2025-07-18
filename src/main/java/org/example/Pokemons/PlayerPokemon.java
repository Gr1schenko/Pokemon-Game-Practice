package org.example.Pokemons;

import org.example.Attacks.DefaultAttack;
import org.example.TypesOfObjects.PokemonType;

public class PlayerPokemon extends Pokemon {
    public PlayerPokemon(PokemonType type, String name, int level, int maxHP) {
        super(type, name, level, maxHP, 1);
    }

    @Override
    protected void initializeAttacks() {
        attacks.add(new DefaultAttack());
    }
}
