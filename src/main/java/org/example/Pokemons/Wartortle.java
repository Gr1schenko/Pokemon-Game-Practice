package org.example.Pokemons;

import org.example.Attacks.CooldownAttackDecorator;
import org.example.Attacks.CrashOfWaveAttack;
import org.example.Attacks.WaterGunAttack;
import org.example.TypesOfObjects.PokemonType;

public class Wartortle extends Pokemon {
    public Wartortle(int level) {
        super(PokemonType.WATER, "Вортортл", level, 50 + level * 7, 2);
    }

    @Override
    protected void initializeAttacks() {
        attacks.add(new CooldownAttackDecorator(new WaterGunAttack(), 1));
        attacks.add(new CooldownAttackDecorator(new CrashOfWaveAttack(), 2));
    }
}
