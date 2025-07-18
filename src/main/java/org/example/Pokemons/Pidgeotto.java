package org.example.Pokemons;

import org.example.Attacks.CooldownAttackDecorator;
import org.example.Attacks.SharpEyeAttack;
import org.example.Attacks.TornadoAttack;
import org.example.TypesOfObjects.PokemonType;

public class Pidgeotto extends Pokemon {
    public Pidgeotto(int level) {
        super(PokemonType.FLYING, "Пиджиотто", level, 50 + level * 7, 2);
    }

    @Override
    protected void initializeAttacks() {
        attacks.add(new CooldownAttackDecorator(new SharpEyeAttack(), 1));
        attacks.add(new CooldownAttackDecorator(new TornadoAttack(), 2));
    }
}
