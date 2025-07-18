package org.example.Pokemons;

import org.example.Attacks.CooldownAttackDecorator;
import org.example.Attacks.ElectroBallAttack;
import org.example.Attacks.LightningStrikeAttack;
import org.example.TypesOfObjects.PokemonType;

public class Raichu extends Pokemon {
    public Raichu(int level) {
        super(PokemonType.ELECTRIC, "Райчу", level, 50 + level * 7, 2);
    }

    @Override
    protected void initializeAttacks() {
        attacks.add(new CooldownAttackDecorator(new LightningStrikeAttack(), 1));
        attacks.add(new CooldownAttackDecorator(new ElectroBallAttack(), 2));
    }
}
