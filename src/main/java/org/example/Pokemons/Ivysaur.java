package org.example.Pokemons;

import org.example.Attacks.CooldownAttackDecorator;
import org.example.Attacks.CuttingSheetAttack;
import org.example.Attacks.GrapeVineAttack;
import org.example.TypesOfObjects.PokemonType;

public class Ivysaur extends Pokemon {
    public Ivysaur(int level) {
        super(PokemonType.GRASS, "Ивизавр", level, 50 + level * 7, 2);
    }

    @Override
    protected void initializeAttacks() {
        attacks.add(new CooldownAttackDecorator(new GrapeVineAttack(), 1));
        attacks.add(new CooldownAttackDecorator(new CuttingSheetAttack(), 2));
    }
}
