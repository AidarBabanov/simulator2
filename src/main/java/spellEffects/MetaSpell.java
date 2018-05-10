package spellEffects;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import targetting.Target;
import player.Player;

import java.util.LinkedList;
import java.util.List;

public class MetaSpell extends SpellEffect {

    private List<SpellEffect> spellEffectList;
    private Player player;

    public MetaSpell() {
        spellEffectList = new LinkedList<>();
    }

    public MetaSpell(JsonObject effect) {
        spellEffectList = new LinkedList<>();
        JsonArray jsonArray = effect.get("spells").getAsJsonArray();
        for (JsonElement jsonEffect : jsonArray) {
            SpellEffect spellEffect = SpellEffect.defineSpellEffect(jsonEffect.getAsJsonObject());
            if (spellEffect != null) this.spellEffectList.add(spellEffect);
        }
    }

    @Override
    public void getArguments(Player player) {
        this.player = player;
    }

    @Override
    public void forceTargets(List<Target> targets) {

    }

    @Override
    public void performAction() {
        for (SpellEffect effect : spellEffectList) {
            if (effect != null) {
                effect.getArguments(player);
                effect.performAction();
            }
        }
    }

    @Override
    public JsonObject jsonStateInformation() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("class", "MetaSpell");
        JsonArray jsonArray = new JsonArray();
        for (SpellEffect spellEffect : spellEffectList) {
            jsonArray.add(spellEffect.jsonStateInformation());
        }
        jsonObject.add("spellEffects", jsonArray);
        return jsonObject;
    }

}
