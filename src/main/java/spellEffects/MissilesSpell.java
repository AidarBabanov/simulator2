package spellEffects;

import com.google.gson.JsonObject;
import player.Player;
import targetting.TargetType;
import targetting.Target;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class MissilesSpell extends SpellEffect {

    private SpellEffect missileSpellEffect;
    private int amount;
    private TargetType targetType;
    private List<Target> targetList;

    public MissilesSpell() {
        targetList = new LinkedList<Target>();
    }

    public MissilesSpell(JsonObject effect) {
        targetList = new LinkedList<Target>();
        DamageSpell damageSpell = new DamageSpell();
        damageSpell.setDamage(effect.get("value").getAsInt());
        this.setAmount(effect.get("howMany").getAsInt());
        this.setMissileSpellEffect(damageSpell);
        this.setTargetType(TargetType.defineTarget(effect));
    }

    @Override
    public void getArguments(Player player) {
        targetList = targetType.getTargets(player);

    }

    @Override
    public void forceTargets(List<Target> targets) {

    }

    @Override
    public void performAction() {
        for (int i = 0; i < amount; i++) {
            int randInt = 0;
            if (targetList.size() > 0) {
                Target target = null;                   //be careful here. By default it should be not alive
                do {
                    if (targetList.size() > 0) randInt = new Random().nextInt() % targetList.size();
                    target = targetList.get(randInt);
                    List<Target> targetList = new LinkedList<>();
                    targetList.add(target);
                    missileSpellEffect.forceTargets(targetList);
                    missileSpellEffect.performAction();
                    if (!target.isAlive()) targetList.remove(target);
                } while (targetList.size() > 0 && !target.isAlive());
            }
        }
    }

    @Override
    public JsonObject jsonStateInformation() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("class", "MissilesSpell");
        jsonObject.add("spell", missileSpellEffect.jsonStateInformation());
        jsonObject.addProperty("amount", amount);
        jsonObject.addProperty("target", targetType.toString());
        return jsonObject;
    }


    public SpellEffect getMissileSpellEffect() {
        return missileSpellEffect;
    }

    public void setMissileSpellEffect(SpellEffect missileSpellEffect) {
        this.missileSpellEffect = missileSpellEffect;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public TargetType getTargetType() {
        return targetType;
    }

    public void setTargetType(TargetType targetType) {
        this.targetType = targetType;
    }
}
