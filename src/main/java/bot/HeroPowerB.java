package bot;

import bot.spells.SpellEffectB;
import com.google.gson.JsonObject;

public class HeroPowerB extends CardB implements SimpleBot.DirectDamageCard {
    private SpellEffectB spellEffectB;
    private boolean used;

    HeroPowerB(JsonObject jsonObject) {
        super(jsonObject);
        this.setSpellEffectB(SpellEffectB.defineSpellEffect(jsonObject.get("spell").getAsJsonObject()));
    }

    public SpellEffectB getSpellEffectB() {
        return spellEffectB;
    }

    public void setSpellEffectB(SpellEffectB spellEffectB) {
        this.spellEffectB = spellEffectB;
    }

    @Override
    public boolean hitsEnemyHero() {
        return spellEffectB.hitsEnemyHero();
    }

    @Override
    public int getDamage() {
        return spellEffectB.getDamage();
    }


    @Override
    public boolean isUsed() {
        return used;
    }

    @Override
    public void setUsed(boolean used) {
        this.used = used;
    }
}
