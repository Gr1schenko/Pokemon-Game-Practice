package org.example.Pokemons;

public class PlayerPokemonFactory implements PokemonFactory {
    public Pokemon createPokemon(String name, int level) {
        switch (name.toLowerCase()) {
            case "пикачу":
                return new Pikachu(level);
            case "чармандер":
                return new Charmander(level);
            case "бульбазавр":
                return new Bulbasaur(level);
            case "пиджи":
                return new Pidgey(level);
            case "сквиртл":
                return new Squirtle(level);
            default:
                throw new IllegalArgumentException("Неизвестный покемон: " + name);
        }
    }
}
