package org.example.Pokemons;

import org.example.Attacks.CooldownAttackDecorator;
import org.example.Attacks.CrashOfWaveAttack;
import org.example.Attacks.WaterGunAttack;
import org.example.TypesOfObjects.PokemonType;

public class Squirtle extends Pokemon {
    public Squirtle(int level) {
        super(PokemonType.WATER, "Сквиртл", level, 30 + level * 5, 1);
    }

    @Override
    protected void initializeAttacks() {
        attacks.add(new CooldownAttackDecorator(new WaterGunAttack(), 1));
        attacks.add(new CooldownAttackDecorator(new CrashOfWaveAttack(), 3));
    }
}
