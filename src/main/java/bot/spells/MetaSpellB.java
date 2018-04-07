package bot.spells;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.LinkedList;
import java.util.List;

public class MetaSpellB extends SpellEffectB {

    private List<SpellEffectB> spellEffectBList;

    public MetaSpellB(JsonObject effect) {
        spellEffectBList = new LinkedList<>();
        JsonArray jsonArray = effect.get("spellEffects").getAsJsonArray();
        for (JsonElement jsonEffect : jsonArray) {
            SpellEffectB spellEffect = SpellEffectB.defineSpellEffect(jsonEffect.getAsJsonObject());
            this.spellEffectBList.add(spellEffect);
        }
    }

    public List<SpellEffectB> getSpellEffectBList() {
        return spellEffectBList;
    }

    public void setSpellEffectBList(List<SpellEffectB> spellEffectBList) {
        this.spellEffectBList = spellEffectBList;
    }

    @Override
    public boolean dealsDamage() {
        boolean result = false;
        for (SpellEffectB spellEffectB : spellEffectBList) {
            result = result || spellEffectB.dealsDamage();
        }
        return result;
    }

    @Override
    public boolean hitsEnemyHero() {
        boolean result = false;
        for (SpellEffectB spellEffectB : spellEffectBList) {
            result = result || spellEffectB.hitsEnemyHero();
        }
        return result;
    }

    @Override
    public int getDamage() {
        int maxDamage = 0;
        for (SpellEffectB spellEffectB : spellEffectBList) {
            maxDamage = Math.max(maxDamage, spellEffectB.getDamage());
        }
        return maxDamage;
    }
}
