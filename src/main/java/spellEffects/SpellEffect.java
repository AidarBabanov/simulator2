package spellEffects;

import com.google.gson.JsonObject;
import player.Player;
import targetting.Target;

import java.util.List;

public abstract class SpellEffect {
    public abstract void getArguments(Player player);
    public abstract void forceTargets(List<Target> targets);
    public abstract void performAction();
    public abstract JsonObject jsonStateInformation();

    public static SpellEffect defineSpellEffect(JsonObject jsonEffect){
        SpellEffect spellEffect = null;
        String spellEffectType = jsonEffect.get("class").getAsString();
        switch (spellEffectType) {
            case "DamageSpell":
                spellEffect = new DamageSpell(jsonEffect);
                break;
            case "DrawCardSpell":
                spellEffect = new DrawCardSpell(jsonEffect);
                break;
            case "MissilesSpell":
                spellEffect = new MissilesSpell(jsonEffect);
                break;
            case "MetaSpell":
                spellEffect = new MetaSpell(jsonEffect);
                break;
        }
        return spellEffect;
    }
}
