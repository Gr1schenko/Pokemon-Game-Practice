package org.example.Pokemons;

import org.example.Attacks.CooldownAttackDecorator;
import org.example.Attacks.CuttingSheetAttack;
import org.example.Attacks.GrapeVineAttack;
import org.example.TypesOfObjects.PokemonType;

public class Bulbasaur extends Pokemon {
    public Bulbasaur(int level) {
        super(PokemonType.GRASS,"Бульбазавр", level, 30 + level * 5, 1);
    }

    @Override
    protected void initializeAttacks() {
        attacks.add(new CooldownAttackDecorator(new GrapeVineAttack(), 1));
        attacks.add(new CooldownAttackDecorator(new CuttingSheetAttack(), 3));
    }
}
