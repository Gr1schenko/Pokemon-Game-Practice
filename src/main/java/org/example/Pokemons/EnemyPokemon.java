package org.example.Pokemons;

import org.example.Attacks.BasicEnemyAttack;
import org.example.Attacks.DefaultAttack;
import org.example.Attacks.StrongEnemyAttack;
import org.example.TypesOfObjects.PokemonType;

public class EnemyPokemon extends Pokemon {
    public EnemyPokemon(String name, int level, int maxHP) {
        super(PokemonType.NONTYPE, name, level, maxHP, 1);
    }

    @Override
    protected void initializeAttacks() {
        attacks.add(new DefaultAttack());
        attacks.add(new BasicEnemyAttack());
        attacks.add(new StrongEnemyAttack());
    }
}
