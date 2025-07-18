package org.example.Pokemons;

public class EnemyPokemonFactory implements PokemonFactory {
    @Override
    public Pokemon createPokemon(String name, int level) {
        return new EnemyPokemon(name, level, 38 + level * 5);
    }
}
