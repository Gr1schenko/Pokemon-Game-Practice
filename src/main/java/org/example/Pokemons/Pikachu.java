package org.example.Pokemons;

import org.example.Attacks.CooldownAttackDecorator;
import org.example.Attacks.ElectroBallAttack;
import org.example.Attacks.LightningStrikeAttack;
import org.example.TypesOfObjects.PokemonType;

public class Pikachu extends Pokemon {
    public Pikachu(int level) {
        super(PokemonType.ELECTRIC, "Пикачу", level, 30 + level * 5, 1);
    }

    @Override
    protected void initializeAttacks() {
        attacks.add(new CooldownAttackDecorator(new LightningStrikeAttack(), 1));
        attacks.add(new CooldownAttackDecorator(new ElectroBallAttack(), 3));
    }
}
