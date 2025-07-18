package org.example.Pokemons;

import org.example.Attacks.CooldownAttackDecorator;
import org.example.Attacks.CuttingSheetAttack;
import org.example.Attacks.GrapeVineAttack;
import org.example.TypesOfObjects.PokemonType;

public class Venusaur extends Pokemon {
    public Venusaur(int level) {
        super(PokemonType.GRASS, "Венузавр", level, 70 + level * 8, 3);
    }

    @Override
    protected void initializeAttacks() {
        attacks.add(new CooldownAttackDecorator(new GrapeVineAttack(), 1));
        attacks.add(new CooldownAttackDecorator(new CuttingSheetAttack(), 1));
    }
}
