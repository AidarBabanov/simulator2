package bot;

import bot.spells.SpellEffectB;
import com.google.gson.JsonObject;

public class SpellB extends CardB implements SimpleBot.DirectDamageCard {

    private SpellEffectB spellEffect;

    public SpellB(JsonObject jsonCard) {
        super(jsonCard);
        String name = jsonCard.get("name").getAsString();
//        String description = jsonCard.get("description").getAsString();
        JsonObject jsonSpellEffect = jsonCard.get("spell").getAsJsonObject();

        this.setName(name);
        this.setSpellEffect(SpellEffectB.defineSpellEffect(jsonSpellEffect));

    }

    public SpellEffectB getSpellEffect() {
        return spellEffect;
    }

    public void setSpellEffect(SpellEffectB spellEffect) {
        this.spellEffect = spellEffect;
    }

    public boolean hitsEnemyHero() {
        return spellEffect.hitsEnemyHero();
    }

    public int getDamage() {
        return spellEffect.getDamage();
    }

}
