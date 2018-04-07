package bot.spells;

import com.google.gson.JsonObject;
import targetting.TargetType;

public class MissilesSpellB extends SpellEffectB {
    private SpellEffectB missileSpellEffect;
    private int amount;
    private TargetType targetType;

    public MissilesSpellB(JsonObject effect) {
        DamageSpellB damageSpell = new DamageSpellB();
        damageSpell.setDamage(effect.get("value").getAsInt());
        this.setAmount(effect.get("howMany").getAsInt());
        this.setMissileSpellEffect(damageSpell);
        this.setTargetType(TargetType.defineTarget(effect));
    }

    public SpellEffectB getMissileSpellEffect() {
        return missileSpellEffect;
    }

    public void setMissileSpellEffect(SpellEffectB missileSpellEffect) {
        this.missileSpellEffect = missileSpellEffect;
    }

    public TargetType getTargetType() {
        return targetType;
    }

    public void setTargetType(TargetType targetType) {
        this.targetType = targetType;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    @Override
    public boolean dealsDamage() {
        return missileSpellEffect.dealsDamage();
    }

    @Override
    public boolean hitsEnemyHero() {
        return true;
    }

    @Override
    public int getDamage() {
        return missileSpellEffect.getDamage();
    }
}
