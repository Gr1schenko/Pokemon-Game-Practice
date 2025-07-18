package org.example.Pokemons;

import org.example.Attacks.CooldownAttackDecorator;
import org.example.Attacks.CrashOfWaveAttack;
import org.example.Attacks.WaterGunAttack;
import org.example.TypesOfObjects.PokemonType;

public class Blastoise extends Pokemon {
    public Blastoise(int level) {
        super(PokemonType.WATER, "Бластойз", level, 70 + level * 8, 3);
    }

    @Override
    protected void initializeAttacks() {
        attacks.add(new CooldownAttackDecorator(new WaterGunAttack(), 1));
        attacks.add(new CooldownAttackDecorator(new CrashOfWaveAttack(), 1));
    }
}