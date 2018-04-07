package bot.spells;

import bot.TargetTypeB;
import com.google.gson.JsonObject;

public class DamageSpellB extends SpellEffectB {
    private int damage;
    private TargetTypeB targetTypeB;

    public DamageSpellB() {
    }

    public DamageSpellB(JsonObject jsonEffect) {
        this.setDamage(jsonEffect.get("value").getAsInt());
        this.setTargetTypeB(TargetTypeB.defineTarget(jsonEffect));

    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public TargetTypeB getTargetTypeB() {
        return targetTypeB;
    }

    public void setTargetTypeB(TargetTypeB targetTypeB) {
        this.targetTypeB = targetTypeB;
    }

    @Override
    public boolean dealsDamage() {
        return true;
    }

    @Override
    public boolean hitsEnemyHero() {
        return targetTypeB.hitsEnemyHero();
    }
}
