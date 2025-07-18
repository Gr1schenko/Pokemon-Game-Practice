package org.example.Pokemons;

import org.example.Attacks.CooldownAttackDecorator;
import org.example.Attacks.FlameAttack;
import org.example.Attacks.FuryOfDragonAttack;
import org.example.TypesOfObjects.PokemonType;

public class Charmander extends Pokemon {
    public Charmander(int level) {
        super(PokemonType.FIRE, "Чармандер", level, 30 + level * 5, 1);
    }

    @Override
    protected void initializeAttacks() {
        attacks.add(new CooldownAttackDecorator(new FlameAttack(), 1));
        attacks.add(new CooldownAttackDecorator(new FuryOfDragonAttack(), 3));
    }
}
