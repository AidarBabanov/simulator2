package bot.spells;

import com.google.gson.JsonObject;

public abstract class SpellEffectB {

    public abstract boolean dealsDamage();

    public abstract boolean hitsEnemyHero();

    public abstract int getDamage();

    public static SpellEffectB defineSpellEffect(JsonObject jsonEffect){
        SpellEffectB spellEffect = null;
        String spellEffectType = jsonEffect.get("class").getAsString();
        switch (spellEffectType) {
            case "DamageSpellB":
                spellEffect = new DamageSpellB(jsonEffect);
                break;
            case "DrawCardSpellB":
                spellEffect = new DrawCardSpellB(jsonEffect);
                break;
            case "MissilesSpell":
                spellEffect = new MissilesSpellB(jsonEffect);
                break;
            case "MetaSpellB":
                spellEffect = new MetaSpellB(jsonEffect);
                break;
        }
        return spellEffect;
    }
}
