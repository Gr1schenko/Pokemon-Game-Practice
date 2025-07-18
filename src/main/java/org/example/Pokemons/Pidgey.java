package org.example.Pokemons;

import org.example.Attacks.CooldownAttackDecorator;
import org.example.Attacks.SharpEyeAttack;
import org.example.Attacks.TornadoAttack;
import org.example.TypesOfObjects.PokemonType;

public class Pidgey extends Pokemon {
    public Pidgey(int level) {
        super(PokemonType.FLYING, "Пиджи", level, 30 + level * 5, 1);
    }

    @Override
    protected void initializeAttacks() {
        attacks.add(new CooldownAttackDecorator(new SharpEyeAttack(), 1));
        attacks.add(new CooldownAttackDecorator(new TornadoAttack(), 3));
    }
}
