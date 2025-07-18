package org.example.Pokemons;

import org.example.Attacks.CooldownAttackDecorator;
import org.example.Attacks.FlameAttack;
import org.example.Attacks.FuryOfDragonAttack;
import org.example.TypesOfObjects.PokemonType;

public class Charmeleon extends Pokemon {
    public Charmeleon(int level) {
        super(PokemonType.FIRE, "Чармелеон", level, 50 + level * 7, 2);
    }

    @Override
    protected void initializeAttacks() {
        attacks.add(new CooldownAttackDecorator(new FlameAttack(), 1));
        attacks.add(new CooldownAttackDecorator(new FuryOfDragonAttack(), 2));
    }
}
